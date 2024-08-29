# Cucumber Disease #5 - "Multiple When Steps":

## Description:
The “Multiple When Steps” code smell violates the fundamental principle of Behavior-Driven Development (BDD): **One Scenario, One Behavior!**
When a scenario contains multiple “When” steps, it indicates an issue within the scope or the purpose of your test case.
Are you using multiple “When” steps are to store multiple results? This leads to unclear and suspicious assertions in the "Then" step.

## Impact:
* Clarity: Combining behaviors into a single scenario leads to ambiguity and miscommunication.
* Root Cause Analysis: When a test fails, identifying the root cause becomes challenging because it could be related to any of the multiple behaviors.
* Duplication: Scenarios that repeat the same sequence of steps instead of isolating behaviors risk unnecessary duplication.

## Your task

TODO: describe details of exercise

* **Merge Steps**: In the case of multiple "When" steps the most obvious solution could be merging the implementation
  into something new. Consider also merging or replacing multiple "When" steps with a  "Then" step. This happens if you
  storing multiple results or there is something suspicious in your assertion.
* **Rearrange Steps**: This is similar to merging steps. Ask your self, which steps are describing the action and which of them are preparing or asserting the scenarios.
Isolate Behaviors: Create separate scenarios for each distinct behavior.