# どこでも家計簿 (Dokodemo Kakeibo)

「どこでも家計簿」は、複数ユーザーが同じ家計を共有しつつ、個別のお小遣い管理や日々の記帳・検索・編集・削除ができるシンプルな Java Web アプリケーションです。  
学習用／小規模チームの家計管理用に設計しています。

---

## 主な機能
- ユーザーごとのログイン（簡易実装）
- 家計の共同管理（同一家計に複数ユーザーがアクセス）
- 支出・収入の記帳（日付、カテゴリ、金額、備考）
- カテゴリ管理（作成 / 編集 / 削除）
- 記録の検索（期間・カテゴリ・キーワード・金額など）
- 記録の編集・削除
- ユーザーごとのお小遣い設定（月ごとではなく、ユーザー単位の固定値で管理）
- DAO パターンによる DB アクセス（MySQL）
- JSP + Servlet ベースの画面構成

---

## 技術スタック
- 言語：Java
- Web：Servlet + JSP
- ビルド：Maven（想定）
- データベース：MySQL
- デザイン：シンプルな HTML/CSS（JSP）
- アーキテクチャ：DAO 層、Service 層（任意）、Controller（Servlet）

---

## 必要条件（Prerequisites）
- Java 8+（プロジェクトに合わせて調整）
- Maven（または Gradle）
- MySQL 5.7+（または互換 DB）
- Tomcat 8/9/10 などの Servlet コンテナ（ローカル開発用）
- （任意）IDE（IntelliJ IDEA, Eclipse 等）

---

## セットアップ手順（ローカル）
1. リポジトリをクローン  
   ```bash
   git clone https://github.com/<your-username>/<your-repo>.git
   cd <your-repo>
   ```

2. データベースを作成（MySQL にログインして実行）  
   ```sql
   CREATE DATABASE dokodemo_kakeibo CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   ```

3. 必要なテーブルの例（サンプル）  
   ```sql
   USE dokodemo_kakeibo;

   CREATE TABLE users (
     user_id INT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(100) NOT NULL UNIQUE,
     password_hash VARCHAR(255) NOT NULL,
     display_name VARCHAR(100),
     allowance INT DEFAULT 0, -- ユーザーごとのお小遣い（単位は円）
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   CREATE TABLE categories (
     category_id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     user_id INT NULL, -- NULLなら共通カテゴリ、特定ユーザー用なら user_id を入れる
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   CREATE TABLE entries (
     entry_id INT AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL,
     date DATE NOT NULL,
     title VARCHAR(255),
     category_id INT,
     credit_flag TINYINT(1) DEFAULT 0, -- 0: 現金, 1: クレジット等
     purpose_flag TINYINT(1) DEFAULT 0,
     price INT NOT NULL,
     note TEXT,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   ALTER TABLE categories ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
   ALTER TABLE entries ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
   ALTER TABLE entries ADD FOREIGN KEY (category_id) REFERENCES categories(category_id);
   ```

4. DB接続設定  
   `db.properties`
   ```
   db.url=jdbc:mysql://localhost:3306/dokodemo_kakeibo?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8
   db.user=root
   db.password=your_password
   ```

5. ビルド & デプロイ  
   ```bash
   mvn clean package
   ```

6. ブラウザでアクセス  
   http://localhost:8080/<context-path>/

---

## 開発メモ
- DAO 層で SQL を分離。
- 日付は LocalDate を使用し、SQL 変換時に java.sql.Date.valueOf(localDate)。
- allowance はユーザー単位で1つの値を保持。

---

## よくあるトラブル
- setDate エラー → java.sql.Date.valueOf(localDate) を使用。
- Unknown column → SQL のカラム名と一致しているか確認。
- 500 エラー → Tomcat ログ (catalina.out) を確認。

---

## ライセンス
MIT License
