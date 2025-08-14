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

## Patient User Stories

**Title**
_As a patient, I want to view a list of doctors without logging into explore options before registering, so that I can decide which one to pick at a glance._

**Acceptance Criteria**
1. Fetch the list of doctors.
2. Ensure latency is within the given threshold.
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 3
**Notes**:
- Display the list nicely with only necessary information that a patient would need at first glance.

**Title**
_As a patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria**
1. Ensure UI is user-friendly and has the appropriate validation.
2. Encrypt the password efficiently to avoid security breaches and data leaks.
3. Ensure the sensitive information stays hidden from the UI for security.
4. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 5
**Notes**:
- Display the form nicely and ensure the latency of the log in and appointment booking is quick and easy

**Title**
_As a patient, I want to log into the portal, so that I can secure my account._

**Acceptance Criteria**
1. Be able to log in with a nice user-friendly UI.
2. Ensure the data is secure and hidden for security
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 2
**Notes**:
- Make sure logging in is quick and efficient to avoid lagging.

**Title**
_As a patient, I want to log out, so that I can safely save all the data and be able to log in at a later time._

**Acceptance Criteria**
1. Ensure all operations are closed and clean to avoid data leaks and maximum security.
2. Ensure latency is within the given threshold.
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 2
**Notes**:
- Ensure operation is quick enough to make it a smooth UX.

**Title**
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria**
1. Ensure the UI is easy to use and operations are efficient.
2. Ensure latency is within the given threshold.
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 5
**Notes**:
- Use confirmation dialogs to confirm reservations and allow a way to notify the patient of the appointment via phone and/or email.

**Title**
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria**
1. Fetch the list of appointments based on most recent to far future events in a neat and user-friendly way.
2. Ensure latency is within the given threshold.
3. Warnings and errors are handled gracefully.

**Priority**: High
**Story Points**: 5
**Notes**:
- Allow the user to be able to click into the appointments to see more details about it.
- Don't need any editing capabilities yet. Just implementing the basics of viewing upcoming appointments at a glance.