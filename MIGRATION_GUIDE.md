# Database Migration Guide: Render to Supabase

This guide documents the step-by-step process of migrating the production database for **E-commerce with Spring Boot** from **Render PostgreSQL** to **Supabase**.

## 🎯 Why Migrate?
We migrated to **Supabase** to achieve:
- **Higher Availability & Stability**: Managed infrastructure with better uptime guarantees.
- **Advanced Features**: Access to real-time capabilities, enhanced security policies, and easier scaling.
- **Connection Reliability**: Solving IPv4/IPv6 connectivity issues between Render (hosting) and external databases using the **Session Pooler**.

## 📋 Prerequisites
Before starting, ensure you have:
1.  **PostgreSQL Client Tools**: Specifically `pg_dump` and `psql`.
    - *Windows Users*: Install [PostgreSQL](https://www.postgresql.org/download/windows/) locally to get these tools in your PATH.
2.  **Supabase Account**: A project created at [supabase.com](https://supabase.com).
3.  **Render Access**: Credentials to your existing database.
4.  **Terminal/Command Prompt**: Administrator privileges recommended on Windows.

---

## 🚀 Step-by-Step Migration Process

### 1. Prepare Supabase (Destination)

1.  Create a new project in Supabase.
2.  Go to **Project Settings** (gear icon) → **Database**.
3.  **Enable Connection Pooling**:
    - Find the "Connection pool" section.
    - Enable it and note the **Session Pooler** details.
    - *Why?* Render's free tier often has IPv6-only routes that conflict with direct DB connections. The Supabase Session Pooler provides an IPv4-compatible endpoint (`aws-0...pooler.supabase.co`).
4.  **Copy Credentials**:
    - Host: `aws-0-[region].pooler.supabase.co`
    - Port: `5432` (or `6543` depending on mode, we use `5432` for standard JDBC).
    - User: `postgres.[your-project-ref]`
    - Password: Set/Reset your database password here.

### 2. Enable External Access on Render (Source)

By default, Render blocks external connections. You must enable them temporarily for the backup.

1.  Go to your Render Dashboard → Select your Database.
2.  Click the **Connect** tab.
3.  Select the **External** tab.
4.  Click **Add External Database User** (if not already created).
5.  Copy the **External Database URL**. It looks like:
    ```text
    postgresql://user:password@host.oregon-postgres.render.com:5432/dbname
    ```

### 3. Export Data from Render (Backup)

Open your terminal (CMD or PowerShell) and navigate to a folder where you want to save the backup (e.g., Desktop).

**Windows Command:**
*(Ensure `pg_dump` is in your PATH or use the full path: `"C:\Program Files\PostgreSQL\17\bin\pg_dump.exe"`)*

```bash
pg_dump "postgresql://USER:PASSWORD@HOST.oregon-postgres.render.com:5432/DATABASE_NAME" -f backup_render.sql --no-owner --clean
```

### 4. Import Data to Supabase (Restore)
You have two options to import the data:

Option A: Using Terminal (Recommended for large datasets) 

``` bash
psql "postgresql://USER:PASSWORD@HOST.pooler.supabase.co:5432/postgres" -f backup_render.sql
```

Note: Use the Supabase Session Pooler URL here.

Option B: Using Supabase SQL Editor (No terminal needed)

1. Open backup_render.sql in a text editor (VS Code, Notepad++).
2. Copy all content (Ctrl+A, Ctrl+C).
3. Go to Supabase Dashboard → SQL Editor → New Query.
4. Paste the content and click Run.

### 5. Update Application Configuration (The Switch)
   Now that data is in Supabase, update your deployment environment.

   Go to Render Dashboard → Your Web Service → Environment.
   Edit the variable SPRING_DATASOURCE_URL.
   Replace the old Render URL with the new JDBC formatted Supabase URL:
   
Format: 
```
jdbc:postgresql://[HOST]:[PORT]/[DB]?user=[USER]&password=[PASSWORD]
```
Example:
```
jdbc:postgresql://aws-0-us-west-2.pooler.supabase.co:5432/postgres?user=postgres.rrdmwfmbnczyraalzgym&password=YourSecurePassword!
```

⚠️ Critical Notes:

    Must start with jdbc:postgresql://.
    Use the Session Pooler host.
    If your password contains special characters (like !, @, #), it is safer to pass them as URL parameters (&password=...) or URL-encode them (e.g., ! → %21).
    Ensure sslmode=require is added if you encounter SSL errors (though usually default in Spring Boot 3).