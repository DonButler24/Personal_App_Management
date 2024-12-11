# **Enhanced Permission Manager**

Enhanced Permission Manager is an Android application designed to empower users by providing detailed insights into their installed applications' permissions and categorizing them based on user-defined risk levels and restrictions. The app also incorporates a risk scoring feature for select applications, making it easier to identify and manage potentially harmful apps.

---

## **Features**

### **1. Application Management**
- Displays a list of all installed non-system applications on the user's device.
- Includes app name, package name, and a risk level indicator.

### **2. Risk Scoring**
- Assigns a predefined risk level to specific applications:
  - **High Risk**: Application with multiple published risk findings
  - **Medium Risk**: Application with 1 published risk findings
  - **Low Risk**: Application with 0 published risk findings
- Risk levels are color-coded:
  - **High Risk**: **Red**
  - **Medium Risk**: **Orange**
  - **Low Risk**: **Green**

### **3. User-defined Restrictions**
- Allows users to categorize applications as:
  - **Unrestricted**: No changes to app permissions.
  - **Restricted**: Future implementation will revoke certain sensitive permissions.

### **4. Permissions Management**
- The app lists commonly used permissions for apps and will include the ability to manage (grant/revoke) permissions dynamically.
- For now, permission management is under development and not fully implemented.

### **5. User-friendly Interface**
- Material Design-inspired UI.
- Toolbar with app name clearly displayed at the top.
- Intuitive dropdown menus for categorizing applications.

---

## **How It Works**
1. **Scan Installed Applications**:
   - The app scans the device for all non-system, user-installed apps.
   - Filters out system apps and apps not installed from trusted sources (e.g., Google Play).

2. **Display Risk Scores**:
   - Risk levels for select apps are predefined and displayed next to their names in the app list.
   - Future updates will integrate a dynamic risk scoring system based on app behaviors and permissions.

3. **Categorize Applications**:
   - Users can select a category (**"Restricted"** or **"Unrestricted"**) for each app using a dropdown menu.
   - Categories are stored locally for now.

---

## **Screenshots**

![image](https://github.com/user-attachments/assets/21bcdd9f-5b3f-4372-8a4a-d6bbc14caaae)

---

## **Planned Features**
1. **Dynamic Risk Scoring**:
   - Implement a database-driven risk scoring system based on factors like:
     - Data retention policies.
     - Privacy violations.
     - Known data breaches.
     - Inadequate security measures.

2. **Custom Categories**:
   - Allow users to create and manage custom app categories with specific rules.

3. **Advanced Permission Management**:
   - Enable dynamic revocation of app permissions for restricted apps.

4. **Integration with System Settings**:
   - Develop features to integrate with Android system settings for better permission control.

---

## **Installation**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/enhanced-permission-manager.git
