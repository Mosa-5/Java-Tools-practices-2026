# Basis

Derived from [`A1_review.md`](A1_review.md) in the same repository.

This grading is based on `A1_review.md` only.

# Internal Marks

## Base Internal Mark: 100/100

Category breakdown:

- Core Assignment Compliance: `35/35`
  - `10/10`: the review confirms `1_000_000` integers for the scalable benchmark scope, with Bubble Sort handled as a documented separate benchmark
  - `10/10`: all required algorithms, including `Arrays.sort(int[])` as a benchmarked algorithm, are present
  - `10/10`: all required input distributions are present
  - `5/5`: Java code, JMH benchmark classes, and a PDF report are present

- Benchmarking Quality: `20/20`
  - `8/8`: real `JMH` usage is present
  - `4/4`: separate benchmark methods are present per algorithm
  - `4/4`: warmup and measurement settings are within the required range
  - `4/4`: benchmark setup clones input arrays and avoids obvious invalidation issues

- Algorithm Correctness and Fidelity: `25/25`
  - `5/5`: Bubble Sort includes early exit
  - `5/5`: Quick Sort is in-place
  - `5/5`: Quick Sort pivot strategy is identifiable and documented
  - `10/10`: Radix Sort matches the assignment requirements

- Correctness Verification: `20/20`
  - `10/10`: comparison against `Arrays.sort()` is present
  - `10/10`: explicit sortedness verification is present

## Bonus Internal Mark: 8/10

Reasoning:

- the repository has a clean benchmark split between scalable algorithms and Bubble Sort
- the report is relevant and analytically useful rather than just a raw output dump
- correctness verification is explicit and structured
- the submission is stronger than the minimum baseline, but the bonus stays short of `10/10` because the work is solid rather than unusually extensive

# Gating Rules Applied

- Rule 1 (`No JMH, No More Than 1/4`): not triggered
- Rule 2 (`Missing Core Benchmark Scope, No More Than 3/4`): not triggered
- Rule 3 (`Broken Correctness Evidence, No More Than 3/4`): not triggered
- Rule 4 (`Bonus Requires Strong Baseline`): satisfied
  - the regular grade reaches `4/4`
  - the review contains no major correctness defect

# Final Grade

- Regular grade: `4/4`
- Bonus grade: `1/1`
- Final grade: `4/4 + 1/1`

# Rationale

This is a fully compliant Assignment 1 submission. The review supports full marks across the core requirement categories, and the repository also clears the bar for the bonus point through clean benchmark structure, a relevant PDF report, and explicit correctness verification.
