## User management
### POST /api/auth/login
#### Request
```json
{
  "username": "string",
  "password": "string"
}
```

#### Response
```json
{
  "token": "string",
  "message": "string"
}
```

#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```

* 400 Bad Request
```json
{
  "success": "boolean",
  "message": "Invalid request format"
}
```


### POST /api/auth/register
#### Request
```json
{
  "username": "string",
  "password": "string", 
  "email": "string"
}
```

#### Response
```json
{
  "message": "string"
}
```
#### Error Responses
* 400 Bad Request
```json
{
  "success": "boolean",
  "message": "Username or mail already exists" 
}
```
## File management
### POST /api/upload - загрузка файла
#### Request
```text
Headers
Authorization: Bearer <token>
Content-Type: application/json
```
```json
{
  "fileName": "string",
  "contentType": "string",
  "fileData": "base64_encoded_file_data"
}
```

#### Response
```json
{
  "success": "boolean",
  "message": "string",
  "fileInfo": {
    "id": "string",
    "fileName": "string",
    "size": "long",
    "contentType": "string",
    "uploadDate": "dateTime",
    "lastDownload": "dateTime",
    "downloadUrl": "string"
  }
}
```
#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```
* 400 Bad Request
```json
{
  "success": "boolean",
  "message": "Invalid request format"
}
```

### GET /download/{id} - скачивание файла
```text
Скачивание файла по ID. Публичный endpoint, не требует авторизации.

Parameters:
fileId
```
#### Response
```json
{
  "id": "string",
  "fileName": "string",
  "contentType":  "string",
  "size": "long",
  "fileData": "byte[]"
}
```
#### Error Responses
* 404 Not Found
```json
{
  "success": "boolean",
  "message": "File not found"
}
```

### GET /api/files - список файлов пользователя
```text
Headers
Authorization: Bearer <token>
```
#### Response
```json
{
    "files": [{
      "id": "string",
      "originalName": "string",
      "size": "number",
      "contentType": "string",
      "uploadDate": "datetime",
      "lastDownload": "datetime|null",
      "downloadUrl": "string"
    }]
}
```
#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```
* 400 Bad Request
```json
{
  "success": "boolean",
  "message": "Invalid request format"
}
```

### GET /api/stats/files - статистика по всем файлам пользователя
```text
Headers
Authorization: Bearer <token>
```
#### Response
```json
{
  "totalFiles": "number",
  "totalSize": "number",
  "totalDownloads": "number",
  "totalDownloadsPerFile": "number",
  "files": [{
    "id": "string",
    "originalName": "string",
    "size": "number",
    "contentType": "string",
    "uploadDate": "datetime",
    "lastDownload": "datetime|null",
    "downloadUrl": "string"
  }]
}
```
#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```

### GET /api/stats/files/{fileId} - статистика по конкретному файлу пользователя
```text
Headers
Authorization: Bearer <token>
```
#### Response
```json
{
  "fileInfo": {
    "id": "string",
    "originalName": "string",
    "size": "number",
    "contentType": "string",
    "uploadDate": "datetime",
    "lastDownload": "datetime|null",
    "downloadUrl": "string"
  },
  "fileStats": {
    "daysSinceUpload": "number",
    "daysSinceLastDownload": "number",
    "downloadsPerDay": "float"
  }
}
```
#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```
* 404 Not Found
```json
{
  "success": "boolean",
  "message": "File not found"
}
```

### DELETE /api/delete/{id} - удаление файла
```text
Headers
Authorization: Bearer <token>

Parameters
fileId
```

#### Response
```json
{
  "success": true,
  "message": "File deleted successfully"
}
```

#### Error Responses
* 401 Unauthorized
```json
{
  "success": "boolean",
  "message": "Invalid credentials"
}
```
* 404 Not Found
```json
{
  "success": "boolean",
  "message": "File not found"
}
```
### Информация о файле
```json
{
  "id": "string",
  "originalName": "string",
  "size": "number",
  "contentType": "string",
  "uploadDate": "datetime",
  "lastDownload": "datetime|null",
  "downloadUrl": "string"
}
```
### Информация о пользователе
```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "active": "boolean"
}
```