# 🚀 Complete API Testing Examples - Demo Zoo Application

## 📚 Quick Reference

| Endpoint | Method | Purpose | Auth Required |
|----------|--------|---------|-----------------|
| `/api/auth/signup` | POST | Register new user | ❌ No |
| `/api/auth/signin` | POST | Login user | ❌ No |
| `/api/animals` | GET | Get all animals | ⚠️ Optional |
| `/api/animals` | POST | Create animal | ✅ Yes |
| `/api/locations` | GET | Get all locations | ⚠️ Optional |
| `/api/locations` | POST | Create location | ✅ Yes |

---

## 🔴 Test Case 1: Invalid Login (What You Saw)

### **Request:**
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "Invalid Username",
    "password": "Invalid Password"
  }'
```

### **Response (HTTP 401):**
```json
{
  "username": "Invalid Username",
  "password": "Invalid Password"
}
```

**Status:** ❌ **FAILED** (as expected for wrong credentials)

---

## 🟢 Test Case 2: Valid Registration

### **Request:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "username": "johndoe",
    "email": "john.doe@example.com",
    "password": "SecurePassword123",
    "confirmPassword": "SecurePassword123"
  }'
```

### **Response (HTTP 200):**
```json
{
  "message": "User registered successfully!"
}
```

**Status:** ✅ **SUCCESS**

---

## 🟢 Test Case 3: Valid Login (After Registration)

### **Request:**
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "SecurePassword123"
  }'
```

### **Response (HTTP 200):**
```json
{
  "success": true,
  "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWQiOjEsImVtYWlsIjoiam9obi5kb2VAZXhhbXBsZS5jb20iLCJmaXJzdG5hbWUiOiJKb2huIiwibGFzdG5hbWUiOiJEb2UiLCJpYXQiOjE2NDU1MTIzODAsImV4cCI6MTY0NTU5ODc4MH0.abc123xyz"
}
```

**Status:** ✅ **SUCCESS**

---

## 🟡 Test Case 4: Invalid Email Format

### **Request:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname": "Smith",
    "username": "janesmith",
    "email": "not-an-email",
    "password": "Password123",
    "confirmPassword": "Password123"
  }'
```

### **Response (HTTP 400):**
```json
{
  "email": "It should have email format"
}
```

**Status:** ⚠️ **VALIDATION ERROR**

---

## 🟡 Test Case 5: Passwords Don't Match

### **Request:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Bob",
    "lastname": "Johnson",
    "username": "bobjohnson",
    "email": "bob@example.com",
    "password": "Password123",
    "confirmPassword": "DifferentPassword123"
  }'
```

### **Response (HTTP 400):**
```json
{
  "confirmPassword": "Passwords don't match"
}
```

**Status:** ⚠️ **VALIDATION ERROR**

---

## 🟡 Test Case 6: Duplicate Username

### **Request (after TestCase2):**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John2",
    "lastname": "Doe2",
    "username": "johndoe",
    "email": "john2@example.com",
    "password": "Password123",
    "confirmPassword": "Password123"
  }'
```

### **Response (HTTP 400/409):**
```json
{
  "error": "User already exists with this username"
}
```

**Status:** ⚠️ **CONFLICT ERROR**

---

## 🔐 Test Case 7: Accessing Protected Endpoint Without Token

### **Request:**
```bash
curl -X GET http://localhost:8080/api/animals
```

### **Response (HTTP 403):**
```json
{
  "username": "Invalid Username",
  "password": "Invalid Password"
}
```

**Status:** ❌ **FORBIDDEN** (Token required)

---

## 🟢 Test Case 8: Accessing Protected Endpoint WITH Token

### **Request:**
```bash
# Используйте token из Test Case 3
JWT_TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWQiOjEsImVtYWlsIjoiam9obi5kb2VAZXhhbXBsZS5jb20iLCJmaXJzdG5hbWUiOiJKb2huIiwibGFzdG5hbWUiOiJEb2UiLCJpYXQiOjE2NDU1MTIzODAsImV4cCI6MTY0NTU5ODc4MH0.abc123xyz"

curl -X GET http://localhost:8080/api/animals \
  -H "Authorization: Bearer $JWT_TOKEN"
```

### **Response (HTTP 200):**
```json
[
  {
    "id": 1,
    "name": "Lion",
    "kind": "PREDATOR",
    "location": "Savanna"
  },
  {
    "id": 2,
    "name": "Zebra",
    "kind": "HERBIVORE",
    "location": "Savanna"
  }
]
```

**Status:** ✅ **SUCCESS**

---

## 📋 Test Execution Order

**Recommended order for complete testing:**

```
1️⃣  Test Case 1: Invalid Login (Verify 401 error) ❌
2️⃣  Test Case 2: Valid Registration (Create user) ✅
3️⃣  Test Case 3: Valid Login (Get JWT token) ✅
4️⃣  Test Case 8: Access Protected Endpoint (Use token) ✅
5️⃣  Test Case 7: No Token (Verify 403 error) ❌
```

---

## 🧪 Testing with Postman

### **Setup:**

1. **Create Collection** → Name it "Zoo API"
2. **Create Environment Variable:**
   - Name: `base_url`
   - Value: `http://localhost:8080`
   - Name: `token`
   - Value: (will be filled after login)

3. **Create Requests:**

#### **Request 1: Signup**
- **Name:** Register User
- **Method:** POST
- **URL:** `{{base_url}}/api/auth/signup`
- **Body (JSON):**
```json
{
  "firstname": "John",
  "lastname": "Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "password": "Password123",
  "confirmPassword": "Password123"
}
```

#### **Request 2: Signin**
- **Name:** Login User
- **Method:** POST
- **URL:** `{{base_url}}/api/auth/signin`
- **Body (JSON):**
```json
{
  "username": "johndoe",
  "password": "Password123"
}
```
- **Tests Tab:**
```javascript
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);
```

#### **Request 3: Get Animals**
- **Name:** Get All Animals
- **Method:** GET
- **URL:** `{{base_url}}/api/animals`
- **Headers:**
  - Key: `Authorization`
  - Value: `{{token}}`

---

## 📊 Status Code Reference

| Code | Meaning | Example |
|------|---------|---------|
| **200** | OK | Login successful, data retrieved |
| **201** | Created | Resource created |
| **400** | Bad Request | Validation error |
| **401** | Unauthorized | Invalid credentials |
| **403** | Forbidden | No token provided |
| **409** | Conflict | User already exists |
| **500** | Server Error | Database issue |

---

## 🔑 JWT Token Structure

Your JWT token contains:

```
Header.Payload.Signature

Header: {
  "alg": "HS512"
}

Payload: {
  "sub": "johndoe",
  "id": 1,
  "email": "john@example.com",
  "firstname": "John",
  "lastname": "Doe",
  "iat": 1645512380,
  "exp": 1645598780
}

Signature: (HMAC-SHA512)
```

**Token expires in:** 24 hours (86400 seconds)

---

## ✨ Quick Copy-Paste Commands

### **Register:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"firstname":"John","lastname":"Doe","username":"johndoe","email":"john@example.com","password":"Password123","confirmPassword":"Password123"}'
```

### **Login:**
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","password":"Password123"}'
```

### **Get Animals (with token):**
```bash
curl -X GET http://localhost:8080/api/animals \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

**Happy Testing! 🚀**

