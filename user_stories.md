# User Story Template

**Title**
_As a [user role], I want [feature/goal], so that [reason]._

**Acceptance Criteria**
1. [Criteria 1]
2. [Criteria 2]
3. [Criteria 3]

**Priority**: [High/Medium/Low]
**Story Points**: [Estimated Effort in Points]
**Notes**:
- [Additional information or edge cases]

## Admin User Stories

**Title**
_As an admin, I want to log into the portal, so that the platform can be managed securely._

**Acceptance Criteria**
1. Be able to log into the portal with username and password.
2. Ensure logging in is done securely.
3. Warnings and errors are handled gracefully.

**Priority**: Medium
**Story Points**: 3
**Notes**:
- Ensure password is encrypted properly to avoid security breaches.
- Ensure all unit tests pass with at least 80% test coverage.

**Title**
_As an admin, I want to log out of the portal, so that the platform can be managed securely._

**Acceptance Criteria**
1. Be able to log out of the portal.
2. Ensure there are no data leakages.
3. Warnings and errors are handled gracefully.

**Priority**: Medium
**Story Points**: 2
**Notes**:
- Make sure to close out everything safely to avoid no data leakage and to protect user details.

**Title**
_As an admin, I want to add doctors to the portal, so that I can manage the platform securely and input the data safely._

**Acceptance Criteria**
1. Pull up a form to be able to add doctors to the portal.
2. Protect the doctors' information as it's being entered and processed.
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 5
**Notes**:
- Ensure there are no security breaches.

**Title**
_As an admin, I want to delete doctor's profile from the portal, so that doctor data can be managed easily and securely._

**Acceptance Criteria**
1. [Criteria 1]
2. [Criteria 2]
3. Warnings and errors are handled gracefully.

**Priority**: Medium
**Story Points**: 3
**Notes**:
- Ensure there is logging/activity history associated with this action for auditing purposes later on.
- Make sure there are no data leakages due to doctors' sensitive information

**Title**
_As an admin, I want to get the number of appointments per month and track usage statistics, so that these numbers can be accessed quickly and efficiently._

**Acceptance Criteria**
1. Create and store a procedure in MySQL CLI to get these statistics.
2. Ensure latency and computational time is short and within the threshold.
3. Warnings and errors are handled gracefully.

**Priority**: Medium
**Story Points**: 3
**Notes**:
- Ensure these numbers are displayed nicely and can be used throughout the application.