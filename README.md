# Rainbow Table Cracker (Java)

This Java project implements a system for generating and using **Rainbow Tables**, which are used to optimize offline brute-force attacks on hashed passwords (specifically MD5 in this case).

## 📌 Context

When a database stores only the **hashes** of user passwords, an attacker who gains access to these hashes must try to reverse the hashing process. Since hash functions are one-way, the only method is to test many candidate inputs and compare the resulting hashes.

Saving every possible password–hash pair requires enormous storage space. Rainbow Tables solve this by storing **only the starting and ending points of hash chains**, and regenerating intermediate values during the lookup phase.

---

## 🗂️ Project Files

### 🔹 `RainbowTable1.java`
- Rainbow Table **without salt**
- Uses a basic reduction function (substring of the hash)
- Hash algorithm is configurable (tested with `MD5`)

### 🔹 `RainbowTable2.java`
- Rainbow Table **with salt** support
- In `getDigest()`, the password is concatenated with a salt before hashing
- Simulates a scenario where each user has a unique salt (making Rainbow Tables ineffective)

### 🔹 `Test1.java`
- Runs test cases using `RainbowTable1`
- Verifies if known hashes can be reversed using the table (no salt)

### 🔹 `Test2.java`
- Runs test cases using `RainbowTable2` (with salt)
- Demonstrates successful decryption of a salted hash and failure of a non-matching one

### 🔹 `TEST_solutions.txt`
- Reference file listing test hashes, expected password results, and system settings

---

## ⚙️ System Settings

- **Password Length**: 4 characters
- **Charset**: `abcdef0123456789` (16 symbols)
- **Hash Algorithm**: MD5
- **Reduction Function**: last 4 characters of the hash
- **Number of Chains**: 2000
- **Chain Length**: 50

---

## ▶️ Sample Output

### Test1 (no salt)

```plaintext
Generating Rainbow Table...
Chain no: 1
...
Decryption result for hash1: 7ba5
Decryption result for hash2: 464c

---

---

## 👤 Autore

**Emanuele Pippa**  
Academic simulation of Rainbow Tables to understand the limitations of unsalted MD5 hashes
GitHub: [epippa](https://github.com/epippa)
