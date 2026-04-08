# Assignment 2 - Refactoring Code Smells

Author: Levan Mosiashvili
Submission folder: `homework2-refactoring/`

This document contains one short note per example. For each one I describe:

1. **Smell** - what was actually wrong (not just the name).
2. **Refactorings applied** - the named Fowler refactorings I used.
3. **Why the result is better** - what the design now communicates.
4. **Behavior preservation** - how I checked the observable behavior is the same as the original. The behavior anchor is the *printed output* of `clientCode()`, not its source. `clientCode` is allowed (and often required) to adapt to the refactored API - new method signatures, new entry points, new collaborators - but the lines it prints must match the original.

Two cases where the printed output intentionally changed (`MutableDataExample`, `RefusedBequestExample`) are called out explicitly in their notes - in both, the original "behavior" was a latent bug that the refactoring removes by construction, which is the entire point of the smell.

One case where `clientCode` was structurally rewritten (`TemporaryFieldExample`) is also called out in its note - the printed output is identical, but the original façade methods disappeared as part of the refactoring, so the entry points `clientCode` calls are different.

## A note on discipline

I deliberately stayed faithful to **only the pointed-out smell** in each example, even when other small improvements were tempting. For instance, in `LoopsExample` I could have made `Student`'s fields `private final` with getters - but that fixes the Data Class smell, not the Loops smell, so I left them as the original mutable package-private fields and only added the predicate the assignment explicitly allows. The same restraint is applied across the package.

---

## 1. Mysterious Name - `MysteriousNameExample.java`

- **Smell:** the method `f` and locals `a, b, c, x, y` carried no intent. A reader had to mentally re-derive what the formula meant.
- **Refactorings:** `Rename Method` (`f` → `halfOfNetAmount`), `Rename Variable` (`a/b/c/x/y` → `quantity / unitPrice / discount / gross / net`).
- **Why better:** the signature is now self-documenting. A reader can answer "what does this compute?" without reading the body.
- **Behavior:** identical arithmetic on identical inputs.

## 2. Duplicated Code - `DuplicatedCodeExample.java`

- **Smell:** `summerInvoice` and `winterInvoice` repeated the tax + shipping rules verbatim. Any change to those rules required two synchronized edits.
- **Refactorings:** `Extract Method` on the shared core (`invoiceTotal`) and on each season-specific discount rule (`summerDiscount`, `winterDiscount`).
- **Why better:** the shared pricing rule lives in one place; each season method now expresses only its own difference (the discount). Adding an "autumn" invoice is one new method.
- **Behavior:** preserved - same totals for any subtotal.

## 3. Long Function - `LongFunctionExample.java`

- **Smell:** `processOrder` mixed five distinct concerns (discount, shipping, tax, approval, formatting) in one ~50-line block.
- **Refactorings:** `Extract Method` for each concern (`discountFor`, `shippingCost`, `approvalStatus`, `formatSummary`). The top-level method now reads as a list of steps in domain language.
- **Why better:** each helper has one reason to change. The top-level method's structure mirrors the business process. Each helper is independently testable.
- **Behavior:** preserved - every branch returns the same numeric values and the summary string is formatted identically.

## 4. Long Parameter List - `LongParameterListExample.java`

- **Smell:** `registerStudent` took 12 raw parameters. The order of arguments was easy to get wrong, and the signature obscured the underlying concepts.
- **Refactorings:** `Introduce Parameter Object` for each cluster - `PersonName`, `ContactInfo`, `Address`, `Guardian`, `Enrollment`. The method now takes 5 named clusters instead of 12 loose primitives.
- **Why better:** the parameter list went from 12 primitives to 5 well-named objects. Each new value type has a single responsibility and is reusable elsewhere.
- **Behavior:** preserved - same summary string is produced from the same data.

## 5. Global Data - `GlobalDataExample.java`

- **Smell:** `currentSemester` and `tuitionRate` were `public static` mutable fields, modifiable from anywhere with no ownership.
- **Refactorings:** `Encapsulate Variable` - moved both into an instance class `SchoolConfig` with controlled mutators (`setCurrentSemester`, `increaseTuition`). Services receive the config by constructor injection rather than reaching to globals.
- **Why better:** every state change goes through a known method on a known instance. Tests can supply a fresh config; concurrent code has a single owner to lock; code search for "who changes the tuition rate" is now exhaustive.
- **Behavior:** preserved - `clientCode` prints the same four lines in the same order.

## 6. Mutable Data - `MutableDataExample.java`

- **Smell:** `getEnrolledStudents()` returned the live internal `ArrayList`. Any caller could `clear()` it and silently erase the class's state - exactly what the original `clientCode` demonstrated.
- **Refactorings:** `Encapsulate Collection` - return `Collections.unmodifiableList(...)`. The only mutation path is the intention-revealing `enroll()`.
- **Why better:** the class actually controls its state. The bug the original `clientCode` showed is now structurally impossible.
- **Behavior - intentional change:** the original `clientCode` exercised the bug (cleared the returned list and observed an empty internal state). After refactoring, that mutation is no longer possible, so `clientCode` is updated to read the size directly. This is the *purpose* of the refactoring, not a regression.

## 7. Divergent Change - `DivergentChangeExample.java`

- **Smell:** one class held three independent concerns - report formatting, SQL persistence, and CSV export - each with their own fields. Three unrelated reasons to change all collided in the same file.
- **Refactorings:** `Extract Class` along the axis of "reason to change": `ReportFormatter`, `SqlInsertBuilder`, `CsvExporter`. Each new class owns only its own fields and methods.
- **Why better:** the report team, the persistence team, and the export team can now change their files independently. Each new class is small and has high cohesion.
- **Behavior:** preserved - each method returns the same string as the original.

## 8. Shotgun Surgery - `ShotgunSurgeryExample.java`

- **Smell:** `Course`, `Invoice`, and `Certificate` each formatted a course title in their own way. Renaming the wording in one place required edits in three classes - the textbook shotgun-surgery shape.
- **Refactorings:** introduced a `CourseTitle` value type that owns every phrasing of the title (`label`, `invoiceDescription`, `certificateText`). Each consumer holds a `CourseTitle` and asks it for the phrasing it needs.
- **Why better:** wording is now changed in one place. Adding a fourth phrasing is one new method on `CourseTitle`, not a hunt across the package.
- **Behavior:** preserved - each consumer prints the same string as before.

## 9. Feature Envy - `FeatureEnvyExample.java`

- **Smell:** `ScholarshipCalculator.qualifies()` only used `StudentAccount` data and contributed nothing of its own - a method living in the wrong class.
- **Refactorings:** `Move Method` - `qualifiesForScholarship()` is now on `StudentAccount` itself. The empty `ScholarshipCalculator` class is removed.
- **Why better:** behavior lives next to the data it depends on. The getters that existed only to feed the calculator can eventually be retired.
- **Behavior:** preserved - same boolean result for the same inputs.

## 10. Data Clumps - `DataClumpsExample.java`

- **Smell:** `(name, email, phone)` traveled together as three separate variables and was passed to four different operations for each of three peers. The same clump kept being reassembled at every call site.
- **Refactorings:** `Introduce Parameter Object` (`ContactInfo`) and `Move Method` for `label` / `emailGreeting` / `smsMessage` / `isReachable` onto it. Client code constructs three `ContactInfo` instances instead of nine raw strings.
- **Why better:** the recurring concept now has a name and a home. Adding a fourth field (e.g. `slack`) is one change instead of four.
- **Behavior:** preserved - same printed output for each peer.

## 11. Primitive Obsession - `PrimitiveObsessionExample.java`

- **Smell:** account status, country, and balance were raw primitives. The eligibility rule `"ACTIVE".equals(...) && "GE".equals(...)` mixed magic strings into a boolean expression, and any caller could pass a typo.
- **Refactorings:** `Replace Type Code with Enum` (`AccountStatus`, `CountryCode`) and `Replace Primitive with Object` (`Money`). `canRentDormRoom` keeps its original location but its parameters are now typed.
- **Why better:** typos become compile errors, the rule reads in domain terms, and `Money` is a place for future arithmetic to live.
- **Behavior:** preserved - same combinations produce the same boolean.

## 12. Repeated Switches - `RepeatedSwitchesExample.java`

- **Smell:** `tuitionDiscount` and `dormPriority` each switched on the same `studentType` string. Adding a new student type required two coordinated edits.
- **Refactorings:** `Replace Type Code with Class/Enum` - a `StudentType` enum carries both the discount rate and the dorm priority. The two switches collapse into delegation.
- **Why better:** new cases live in exactly one place (the enum constant). The compiler now enforces that every type defines both attributes.
- **Behavior:** preserved - same return values for the same inputs.

## 13. Loops - `LoopsExample.java`

- **Smell:** an explicit `for`-loop with an internal `if` was used to express a simple filter+map.
- **Refactorings:** `Replace Loop with Pipeline` (`stream().filter(...).map(...).collect(...)`). The predicate is named (`Student::isHonorStudent`) so the rule reads as domain language.
- **Why better:** the pipeline expresses *what* is being done, not *how* it iterates. The predicate is reusable.
- **Behavior:** preserved - same names in the same order.

## 14. Lazy Element - `LazyElementExample.java`

- **Smell:** `StudentNameFormatter` existed only to call `String.trim()`. The class added a layer with no behavior of its own.
- **Refactorings:** `Inline Class` - removed the helper; the caller uses `String.trim()` directly. The proposed refactorings list this as the correct outcome.
- **Why better:** one fewer pointless type. There was no policy worth preserving; the abstraction was pure noise.
- **Behavior:** preserved - same printed value.

## 15. Speculative Generality - `SpeculativeGeneralityExample.java`

- **Smell:** `send(message, futureTemplate, encrypted, urgent)` carried three extra parameters that the only implementation completely ignored. The interface was prepared for variation that did not exist.
- **Refactorings:** `Remove Dead Parameter` × 3 - the interface now expresses what is actually used: `send(message)`.
- **Why better:** the API stops lying about its capabilities. New implementations only need to do the one job that exists.
- **Behavior:** preserved - same string is printed for the same call.

## 16. Temporary Field - `TemporaryFieldExample.java`

- **Smell:** `examRoom` was meaningful only for onsite exams and `onlineMeetingLink` only for online exams. The object always had at least one irrelevant field.
- **Refactorings:** `Extract Class` - `OnsiteExam` owns the room, `OnlineExam` owns the link. Then `Inline Class` on the now-empty façade: once the mode-specific state was moved out, the original class had nothing left except routing, and the boolean parameters that existed only to choose a mode disappeared with it.
- **Why better:** each new class has 100% relevant state, and the awkward boolean parameters that the original used to pick a mode are gone. Callers use whichever extracted class they actually need.
- **Behavior:** the *printed output* is preserved - `clientCode` prints the same three lines as the original. The *shape* of `clientCode` was rewritten because the façade methods (`prepareOnsiteExam`, `prepareOnlineExam`) no longer exist after Inline Class. This is a structural rewrite of the entry points, not a change in observable behavior.

## 17. Message Chains - `MessageChainsExample.java`

- **Smell:** `university.getDepartment().getCoordinator().getOffice().getPhoneNumber()` walked through four objects to get one value, coupling the caller to the entire object graph.
- **Refactorings:** `Hide Delegate` - `University.getCoordinatorPhone()` bubbles the value up. The intermediate types are no longer part of the caller's vocabulary.
- **Why better:** the caller depends on `University` only. Restructuring the internals (e.g. moving the office under the coordinator's manager) does not affect callers.
- **Behavior:** preserved - same phone number is printed.

## 18. Middle Man - `MiddleManExample.java`

- **Smell:** `StudentPortal.findGrade()` simply forwarded to `TranscriptService.findGrade()` with no extra policy.
- **Refactorings:** `Remove Middle Man` - callers talk to `TranscriptService` directly; the empty portal class is removed.
- **Why better:** one fewer layer to keep in sync. (If the portal ever gains real policy - auth, audit log, caching - it can come back. Right now it pays for itself with nothing.)
- **Behavior:** preserved - same grade is printed for the same id.

## 19. Insider Trading - `InsiderTradingExample.java`

- **Smell:** `AuditService` reached into `BankAccount.balance` and wrote to `BankAccount.secretFlag` directly. The two classes shared too much knowledge of each other's internals, and every account-level rule had to live in the audit service.
- **Refactorings:** `Encapsulate Field` (balance and frozen flag are now private), `Move Method` (the freeze rule lives on `BankAccount` as `freezeIfOverdrawn()`). The audit service simply asks the account to enforce its own invariant.
- **Why better:** the account owns its own invariants. The audit service no longer has to be updated when the freeze rule changes.
- **Behavior:** preserved - an overdrawn account ends in the FROZEN state.

## 20. Large Class - `LargeClassExample.java`

- **Smell:** one class held enrollment lists, staffing, courses, finance, help-desk tickets, cafeteria menu, bus schedule, website theme and payroll day - eight unrelated responsibilities.
- **Refactorings:** `Extract Class` along seven cohesive axes - `Enrollment`, `Staff`, `Curriculum`, `Finance`, `HelpDesk`, `Facilities`, `WebsiteSettings`. The remaining `School` class is a thin façade that owns the new collaborators.
- **Why better:** each new class has one reason to change and a small surface area. A change to payroll no longer touches the same file as a change to website theme.
- **Behavior:** preserved - `clientCode` performs the same operations through the new façade and the same data ends up in the same places.

## 21. Alternative Classes with Different Interfaces - `AlternativeClassesWithDifferentInterfacesExample.java`

- **Smell:** `ZoomClassroom.beginSession()` and `TeamsClassroom.openMeeting()` did the same job under different names, so any caller had to branch on concrete type instead of using one shared protocol.
- **Refactorings:** introduced a `Classroom` interface with `start()`. Both implementations adopt the shared name; the client now treats them uniformly.
- **Why better:** new vendors slot in without changes to any caller. The shared protocol is the contract.
- **Behavior:** preserved - same lines printed.

## 22. Data Class - `DataClassExample.java`

- **Smell:** `StudentRecord` was a bag of public fields. Three external "calculator" classes - `HonorsEvaluator`, `TuitionDiscountCalculator`, `AcademicStandingReporter` - reached into it to make decisions, leaving the record itself passive.
- **Refactorings:** `Encapsulate Field` (name/credits/gpa become private with a constructor), `Move Method` (`isHonorsEligible`, `tuitionDiscountPercent`, `academicStanding` now live on `StudentRecord`). The three external classes are removed because they had no behavior of their own.
- **Why better:** the record now models the student instead of just storing data. Behavior lives next to the fields it reads.
- **Behavior:** preserved - `clientCode` prints the same three values.

## 23. Refused Bequest - `RefusedBequestExample.java`

- **Smell:** `Penguin extends Bird` only to throw `UnsupportedOperationException` from `fly()`. The inheritance lied about the contract: any code that thought it had a `Bird` could blow up at runtime.
- **Refactorings:** `Push Down Method` / restructured hierarchy - `Bird` is the common type with no `fly()`; a new `FlyingBird` subclass adds `fly()`. `Sparrow` and `Eagle` extend `FlyingBird`; `Penguin` extends `Bird` only.
- **Why better:** "ask a penguin to fly" is now a compile error rather than a runtime exception. The Liskov substitution principle is restored: every `FlyingBird` really can fly.
- **Behavior - intentional change:** the original `penguin.fly()` call threw at runtime. The whole point of this refactoring is to make that call structurally invalid, so `clientCode` no longer calls `fly()` on the penguin (the local is created so the demonstration is concrete, with a comment marking why no method is called on it). This is the intended outcome of the refactoring, not a regression.

## 24. Comments - `CommentsExample.java`

- **Smell:** `finalPrice` had three line-comments narrating each step. The comments were necessary because the code ("`result = result - result * 0.10`" on a variable called `result`) could not stand on its own.
- **Refactorings:** `Extract Method` for each step (`applyVipDiscount`, `applyBulkDiscount`, `withTax`), `Rename Variable` so the locals describe stages (`afterVipDiscount`, `afterBulkDiscount`), and named constants for the magic numbers. The comments are then deleted because the code already says what they said.
- **Why better:** the method names *are* the explanation. There is no longer a way for the code and the comments to drift out of sync.
- **Behavior:** preserved - same final price for the same inputs.

