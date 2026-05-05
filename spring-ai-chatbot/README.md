# 🤖 Spring AI Chatbot (Free LLM via Groq)

A full-stack chatbot built with **Spring Boot 3** + **Spring AI** + **Groq** (free LLM API).  
Supports multi-turn conversations and streaming responses.

---

## 🆓 Getting Your Free API Key (Groq)

1. Go to [https://console.groq.com](https://console.groq.com)
2. Sign up for a **free account** (no credit card required)
3. Navigate to **API Keys → Create API Key**
4. Copy the key — it starts with `gsk_...`

Groq's free tier is very generous: ~14,400 requests/day on Llama 3.3 70B.

---

## ⚙️ Setup

### 1. Set your API key

**Option A — Environment variable (recommended):**
```bash
export GROQ_API_KEY=gsk_your_key_here
```

**Option B — Edit `application.properties` directly:**
```properties
spring.ai.openai.api-key=gsk_your_key_here
```

### 2. Run the app

```bash
./mvnw spring-boot:run
```

Or build and run the jar:
```bash
./mvnw clean package
java -jar target/spring-ai-chatbot-1.0.0.jar
```

### 3. Open your browser

```
http://localhost:8080
```

---

## 🧠 Available Free Models (on Groq)

Change the model in `application.properties`:

| Model | Best For |
|-------|----------|
| `llama-3.3-70b-versatile` | Best quality (default) |
| `llama-3.1-8b-instant` | Fastest responses |
| `mixtral-8x7b-32768` | Long context (32K) |
| `gemma2-9b-it` | Google's Gemma 2 |

---

## 📁 Project Structure

```
spring-ai-chatbot/
├── pom.xml
└── src/main/
    ├── java/com/chatbot/
    │   ├── ChatbotApplication.java      ← Entry point
    │   ├── config/
    │   │   └── ChatConfig.java          ← ChatClient bean + system prompt
    │   ├── controller/
    │   │   └── ChatController.java      ← REST endpoints
    │   └── model/
    │       └── ChatModels.java          ← Request/Response records
    └── resources/
        ├── application.properties       ← Config (API key, model)
        └── static/
            └── index.html               ← Built-in chat UI
```

---

## 🔌 API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| `POST` | `/api/chat` | Regular JSON response |
| `POST` | `/api/chat/stream` | Streaming (SSE) response |
| `GET` | `/api/chat/health` | Health check |

### Example (cURL):
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What is Spring AI?", "history": []}'
```

---

## 🛠 Requirements

- Java 17+
- Maven 3.8+
