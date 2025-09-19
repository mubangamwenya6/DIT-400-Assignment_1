#include <iostream>
#include <fstream>
#include <string>
using namespace std;

const int MAX_COURSES = 100;
const int MAX_USERS = 50;
const string USER_FILE = "users.txt";
const string COURSE_FILE = "courses.txt";

string courseIds[MAX_COURSES];
string titles[MAX_COURSES];
int creditHours[MAX_COURSES];
int courseCount = 0;

string usernames[MAX_USERS];
string passwords[MAX_USERS];
int userCount = 0;

void loadUsers() {
    ifstream file(USER_FILE);
    if (!file.is_open()) return;
    
    string line;
    while (getline(file, line) && userCount < MAX_USERS) {
        int commaPos = line.find(',');
        if (commaPos != string::npos) {
            usernames[userCount] = line.substr(0, commaPos);
            passwords[userCount] = line.substr(commaPos + 1);
            userCount++;
        }
    }
    file.close();
}

void loadCourses() {
    ifstream file(COURSE_FILE);
    if (!file.is_open()) return;
    
    string line;
    while (getline(file, line) && courseCount < MAX_COURSES) {
        int comma1 = line.find(',');
        int comma2 = line.find(',', comma1 + 1);
        
        if (comma1 != string::npos && comma2 != string::npos) {
            courseIds[courseCount] = line.substr(0, comma1);
            titles[courseCount] = line.substr(comma1 + 1, comma2 - comma1 - 1);
            creditHours[courseCount] = stoi(line.substr(comma2 + 1));
            courseCount++;
        }
    }
    file.close();
}

void saveCourses() {
    ofstream file(COURSE_FILE);
    for (int i = 0; i < courseCount; i++) {
        file << courseIds[i] << "," << titles[i] << "," << creditHours[i] << endl;
    }
    file.close();
}

bool registerUser() {
    string username, password;
    cout << "Enter username: ";
    getline(cin, username);
    cout << "Enter password: ";
    getline(cin, password);
    
    for (int i = 0; i < userCount; i++) {
        if (usernames[i] == username) {
            return false;
        }
    }
    
    if (userCount < MAX_USERS) {
        usernames[userCount] = username;
        passwords[userCount] = password;
        userCount++;
        
        ofstream file(USER_FILE, ios::app);
        file << username << "," << password << endl;
        file.close();
        return true;
    }
    
    return false;
}

bool login() {
    string username, password;
    cout << "Username: ";
    getline(cin, username);
    cout << "Password: ";
    getline(cin, password);
    
    for (int i = 0; i < userCount; i++) {
        if (usernames[i] == username && passwords[i] == password) {
            return true;
        }
    }
    return false;
}

int findCourseById(string courseId) {
    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == courseId) {
            return i;
        }
    }
    return -1;
}

void addCourse() {
    if (courseCount >= MAX_COURSES) {
        cout << "Maximum courses reached!" << endl;
        return;
    }
    
    string id, title;
    int hours;
    
    cout << "Enter Course ID: ";
    getline(cin, id);
    
    if (findCourseById(id) != -1) {
        cout << "Course ID already exists!" << endl;
        return;
    }
    
    cout << "Enter Course Title: ";
    getline(cin, title);
    
    cout << "Enter Credit Hours (1-6): ";
    cin >> hours;
    cin.ignore();
    
    if (hours < 1 || hours > 6) {
        cout << "Invalid credit hours!" << endl;
        return;
    }
    
    courseIds[courseCount] = id;
    titles[courseCount] = title;
    creditHours[courseCount] = hours;
    courseCount++;
    
    saveCourses();
    cout << "Course added!" << endl;
}

void deleteCourse() {
    string id;
    cout << "Enter Course ID to delete: ";
    getline(cin, id);
    
    int index = findCourseById(id);
    if (index == -1) {
        cout << "Course not found!" << endl;
        return;
    }
    
    for (int i = index; i < courseCount - 1; i++) {
        courseIds[i] = courseIds[i + 1];
        titles[i] = titles[i + 1];
        creditHours[i] = creditHours[i + 1];
    }
    
    courseCount--;
    saveCourses();
    cout << "Course deleted!" << endl;
}

void searchCourse() {
    cout << "Search by: 1. ID 2. Title: ";
    int choice;
    cin >> choice;
    cin.ignore();
    
    if (choice == 1) {
        string id;
        cout << "Enter Course ID: ";
        getline(cin, id);
        
        int index = findCourseById(id);
        if (index != -1) {
            cout << "Found: " << courseIds[index] << " - " 
                 << titles[index] << " (" << creditHours[index] << " hours)" << endl;
        } else {
            cout << "Not found!" << endl;
        }
    } else if (choice == 2) {
        string keyword;
        cout << "Enter keyword: ";
        getline(cin, keyword);
        
        bool found = false;
        for (int i = 0; i < courseCount; i++) {
            if (titles[i].find(keyword) != string::npos) {
                cout << courseIds[i] << " - " << titles[i] 
                     << " (" << creditHours[i] << " hours)" << endl;
                found = true;
            }
        }
        
        if (!found) {
            cout << "No courses found!" << endl;
        }
    } else {
        cout << "Invalid choice!" << endl;
    }
}

void updateCourse() {
    string id;
    cout << "Enter Course ID to update: ";
    getline(cin, id);
    
    int index = findCourseById(id);
    if (index == -1) {
        cout << "Course not found!" << endl;
        return;
    }
    
    cout << "Current title: " << titles[index] << endl;
    cout << "New title (press Enter to keep): ";
    string newTitle;
    getline(cin, newTitle);
    if (!newTitle.empty()) {
        titles[index] = newTitle;
    }
    
    cout << "Current hours: " << creditHours[index] << endl;
    cout << "New hours (1-6, 0 to keep): ";
    int newHours;
    cin >> newHours;
    cin.ignore();
    
    if (newHours >= 1 && newHours <= 6) {
        creditHours[index] = newHours;
    }
    
    saveCourses();
    cout << "Course updated!" << endl;
}

void listCourses() {
    if (courseCount == 0) {
        cout << "No courses!" << endl;
        return;
    }
    
    cout << "All courses:" << endl;
    for (int i = 0; i < courseCount; i++) {
        cout << i+1 << ". " << courseIds[i] << " - " 
             << titles[i] << " (" << creditHours[i] << " hours)" << endl;
    }
}

void showMainMenu() {
    while (true) {
        cout << "\n=== MAIN MENU ===" << endl;
        cout << "1. Add Course" << endl;
        cout << "2. Delete Course" << endl;
        cout << "3. Search Course" << endl;
        cout << "4. Update Course" << endl;
        cout << "5. List Courses" << endl;
        cout << "6. Logout" << endl;
        cout << "Choose: ";
        
        int choice;
        cin >> choice;
        cin.ignore();
        
        switch (choice) {
            case 1: addCourse(); break;
            case 2: deleteCourse(); break;
            case 3: searchCourse(); break;
            case 4: updateCourse(); break;
            case 5: listCourses(); break;
            case 6: return;
            default: cout << "Invalid choice!" << endl;
        }
        
        cout << "Press Enter to continue...";
        cin.get();
    }
}

int main() {
    loadUsers();
    loadCourses();
    
    while (true) {
        cout << "=== COURSE SYSTEM ===" << endl;
        cout << "1. Login" << endl;
        cout << "2. Register" << endl;
        cout << "3. Exit" << endl;
        cout << "Choose: ";
        
        int choice;
        cin >> choice;
        cin.ignore();
        
        if (choice == 1) {
            if (login()) {
                showMainMenu();
            } else {
                cout << "Login failed!" << endl;
            }
        } else if (choice == 2) {
            if (registerUser()) {
                cout << "Registration success!" << endl;
            } else {
                cout << "Username taken!" << endl;
            }
        } else if (choice == 3) {
            cout << "Goodbye!" << endl;
            break;
        } else {
            cout << "Invalid choice!" << endl;
        }
    }
    
    return 0;
}