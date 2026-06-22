# 🔮 BookVerse - Cosmic Personal Library & Tracker

Welcome to **BookVerse**, a comprehensive, localized Spring Boot web ecosystem designed for passionate readers to curate library archives, write community reviews, and manifest active reading milestones over a synchronized interactive interface.

---

## 🚀 Architectural & User Capabilities

### 👥 Authenticated Users Can:
* **🔭 Explore Catalog**: Browse globally available titles, filter by genres, and check overall community descriptions.
* **🪐 Manage Personal Shelves**: Categorize books seamlessly across three life-cycle stages:
    * 🌑 **Want to Read**
    * 🌓 **Currently Reading**
    * 🌕 **Completed**
* **🔄 Move & Relocate Books**: Shift book states inline instantly on the dashboard using dynamic status selectors.
* **🎯 Manifest Goals**: Create quantitative volume achievements or qualitative genre challenges with real-time tracking metrics.
* **✍️ Write Reviews**: Leave cosmic summaries and project thoughts straight into the universal dashboard.

---

## 🔒 Security & Data Integrity
* **Password Security**: All user credentials are obfuscated and securely processed using **BCrypt cryptographic hashing** before persistent database serialization.
* **Session Matrix**: Protected state flows ensure guests are safely routed to authentication boundaries before interacting with shelves or goals.

---

## 🛡️ Input Validation Matrix
To protect data layer integrity, all form submission objects are bounded by rigorous backend and server-side validation properties:
* **Required Inputs**: Titles, descriptions, and authentication parameters cannot pass empty frames.
* **Boundary Length Constraints**: Restricts inputs to realistic allocations (e.g., specific minimum/maximum character thresholds for registration or text descriptions).
* **Date & Calendar Validation**: Challenge deadlines ensure timeline configurations remain logic-accurate.
* **Automated Sync Listeners**: State alterations across shelves automatically trigger progress evaluations dynamically.

---

## 💻 Tech Stack
* **Backend Framework**: Java / Spring Boot
* **Template Engine**: Thymeleaf Matrix
* **Styling & UI Components**: Bootstrap 5.3 + Custom CSS Glassmorphism