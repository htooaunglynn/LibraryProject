package com.librarymanagementsystem.model;

import com.librarymanagementsystem.LibraryApplication;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseService {

    public static Admin login(String email, String password) {
        Admin admin = null;

        try(var con = MyConnection.createConnection()) {

            var sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
            var pstm = con.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, password);

            var rs = pstm.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    public static void saveCategory(String name) throws Exception {

        try (var con = MyConnection.createConnection()) {
            var sql = "INSERT INTO category(name, created_at, updated_at, created_by)VALUES(?, ?, ?, ?)";
            var pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setDate(2, Date.valueOf(LocalDate.now()));
            pstm.setDate(3, Date.valueOf(LocalDate.now()));
            pstm.setInt(4, LibraryApplication.loginAdmin.getId());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Category> findAllCategory() {
        List<Category> categoryList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {
            var sql = "SELECT * FROM category ORDER BY created_at";
            var pstm = con.prepareStatement(sql);

            var rs = pstm.executeQuery();

            while (rs.next()) {
                var category = new Category();

                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setCreatedAt(LocalDate.parse(rs.getString("created_at")));
                category.setUpdatedAt(LocalDate.parse(rs.getString("updated_at")));

                categoryList.add(category);
            }
        } catch (Exception e) {

        }

        return categoryList;
    }

    public static void updateCategory(Category category) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "UPDATE category SET name = ?, updated_at = ? WHERE id = ?";
            var pstm = con.prepareStatement(sql);
            pstm.setString(1, category.getName());
            pstm.setInt(3, category.getId());
            pstm.setDate(2, Date.valueOf(LocalDate.now()));

            pstm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Author> findAllAuthor() {
        List<Author> authorList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {
            var sql = """
                    SELECT a.*, ad.email
                    FROM author a, admin ad
                    WHERE a.created_by = ad.id
                    """;
            var pstm = con.prepareStatement(sql);

            var rs = pstm.executeQuery();

            while (rs.next()) {
                var author = new Author();

                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setCity(rs.getString("city"));
                author.setBirthday(LocalDate.parse(rs.getString("birthday")));

                var created_by = new Admin();
                created_by.setEmail(rs.getString("email"));
                created_by.setId(rs.getInt("created_by"));

                author.setAdmin(created_by);

                authorList.add(author);
            }

        } catch (Exception e) {

        }

        return authorList;
    }

    public static void addAuthor(String name, String city, LocalDate birthday) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "INSERT INTO author(name, city, birthday, created_by)VALUES(?, ?, ?, ?)";
            var pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, city);
            pstm.setDate(3, Date.valueOf(birthday));
            pstm.setInt(4, LibraryApplication.loginAdmin.getId());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    public static void editAuthor(Author author) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "UPDATE author SET name = ?, city = ?, birthday = ? WHERE id = ?";
            var pstm = con.prepareStatement(sql);
            pstm.setString(1, author.getName());
            pstm.setString(2, author.getCity());
            pstm.setDate(3, Date.valueOf(author.getBirthday()));
            pstm.setInt(4, author.getId());

            pstm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }

    }

    public static List<Book> listAllBook() {
        List<Book> bookList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {
            var sql = """
                    SELECT b.code, b.title, b.public_date, a.name 'author', c.name 'category', b.available ,ad.email
                    FROM book b, author a, category c, admin ad
                    WHERE b.author_id = a.id AND
                    b.created_by = ad.id AND
                    b.category_id = c.id
                    """;
            var prepareStatement = con.prepareStatement(sql);

            var rs = prepareStatement.executeQuery();

            while (rs.next()) {
                var book = new Book();

                book.setCode(rs.getInt("code"));
                book.setTitle(rs.getString("title"));
                book.setPublicDate(LocalDate.parse(rs.getString("public_date")));
                book.getAuthor().setName(rs.getString("author"));
                book.getCategory().setName(rs.getString("category"));
                book.setAvailable(rs.getBoolean("available"));
                book.getAdmin().setEmail(rs.getString("email"));

                bookList.add(book);
            }
        } catch (Exception e) {

        }

        return bookList;
    }

    public static void addBook(Book book) throws Exception {
        try (var con = MyConnection.createConnection()) {

            var sql = "INSERT INTO book(code, title, public_date, author_id, category_id, available, created_by) VALUES(?, ?, ?, ?, ?, ?, ?)";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setInt(1, book.getCode());
            prepareStatement.setString(2, book.getTitle());
            prepareStatement.setDate(3, Date.valueOf(book.getPublicDate()));
            prepareStatement.setInt(4, book.getAuthor().getId());
            prepareStatement.setInt(5, book.getCategory().getId());
            prepareStatement.setBoolean(6, true);
            prepareStatement.setInt(7, LibraryApplication.loginAdmin.getId());

            prepareStatement.executeUpdate();


        } catch (Exception e) {
            throw e;
        }
    }

    public static Book findByBookId(int id) {
        Book book = null;

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    SELECT b.category_id, b.author_id, b.code, b.title, b.public_date, a.name 'author', c.name 'category', b.available ,ad.email
                    FROM book b, author a, category c, admin ad
                    WHERE b.author_id = a.id AND
                    b.created_by = ad.id AND
                    b.category_id = c.id AND
                    b.code = ?
                    """;

            var prepareStatement = con.prepareStatement(sql);
            prepareStatement.setInt(1, id);

            var rs = prepareStatement.executeQuery();

            if (rs.next()) {
                book = new Book();
                book.setCode(rs.getInt("code"));
                book.setTitle(rs.getString("title"));
                book.setPublicDate(LocalDate.parse(rs.getString("public_date")));

                var author = new Author();
                author.setName(rs.getString("author"));
                author.setId(rs.getInt("author_id"));

                var category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category"));

                book.setCategory(category);
                book.setAuthor(author);
            }

        } catch (Exception e) {

        }

        return book;
    }

    public static void editBook(Book book) throws Exception {
        try (var con = MyConnection.createConnection()) {

            var sql = "UPDATE book SET title = ?, category_id = ?, author_id = ? WHERE code = ?";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setInt(2, book.getCategory().getId());
            prepareStatement.setInt(3, book.getAuthor().getId());
            prepareStatement.setInt(4, book.getCode());

            prepareStatement.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteBook(int code) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "DELETE FROM book WHERE code = ?";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setInt(1, code);
            prepareStatement.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Book> searchBook(String title, String author, String category) {
        List<Book> bookList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    SELECT b.code, b.title, b.public_date, a.name 'author', c.name 'category', b.available ,ad.email
                    FROM book b, author a, category c, admin ad
                    WHERE b.author_id = a.id AND
                    b.created_by = ad.id AND
                    b.category_id = c.id
                    """;

            List<Object> params = new ArrayList<>();

            if (!author.equals("")) {
                sql += "AND a.name LIKE ?";
                params.add("%" + author + "%");
            }

            if (!category.equals("")) {
                sql += "AND c.name LIKE ?";
                params.add("%" + category + "%");
            }

            if (!title.equals("")) {
                sql += "AND b.title LIKE ?";
                params.add("%" + title + "%");
            }

            var prepareStatement = con.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                prepareStatement.setObject(i+1, params.get(i));
            }

            var rs = prepareStatement.executeQuery();

            while (rs.next()) {

                var book = new Book();

                book.setCode(rs.getInt("code"));
                book.setTitle(rs.getString("title"));
                book.setPublicDate(LocalDate.parse(rs.getString("public_date")));
                book.getAuthor().setName(rs.getString("author"));
                book.getCategory().setName(rs.getString("category"));
                book.setAvailable(rs.getBoolean("available"));
                book.getAdmin().setEmail(rs.getString("email"));

                bookList.add(book);

            }


        } catch (Exception e) {

        }

        return bookList;
    }

    public static List<Admin> findAllAdmin() {
        List<Admin> adminList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    SELECT * FROM admin
                    """;
            var pstm = con.prepareStatement(sql);

            var rs = pstm.executeQuery();

            while (rs.next()) {
                var admin = new Admin();

                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setNrc(rs.getString("nrc"));
                admin.setPhone(rs.getString("phone"));

                adminList.add(admin);
            }

        } catch (Exception e) {

        }

        return adminList;
    }

    public static void addAdmin(Admin admin) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "INSERT INTO admin(email, password, nrc, phone)VALUES(?, ?, ?, ?)";
            var pstm = con.prepareStatement(sql);

            pstm.setString(1, admin.getEmail());
            pstm.setString(2, admin.getPassword());
            pstm.setString(3, admin.getNrc());
            pstm.setString(4, admin.getPhone());

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    public static void updateAdmin(Admin admin) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "UPDATE admin SET email = ?, password = ?, nrc = ?, phone = ? WHERE id = ?";
            var pstm = con.prepareStatement(sql);
            pstm.setString(1, admin.getEmail());
            pstm.setString(2, admin.getPassword());
            pstm.setString(3, admin.getNrc());
            pstm.setString(4, admin.getPhone());
            pstm.setInt(5, admin.getId());

            pstm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void studentRegister(Student student) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    INSERT INTO student(name, roll_no, year, academic_year, created_date, expired_date) 
                    VALUES(?, ?, ?, ?, ?, ?)
                    """;
            var pstm = con.prepareStatement(sql);

            pstm.setString(1, student.getName());
            pstm.setInt(2, student.getRollNumber());
            pstm.setString(3, student.getYear());
            pstm.setString(4, student.getAcademicYear());
            pstm.setDate(5, Date.valueOf(LocalDate.now()));
            pstm.setDate(6, Date.valueOf(LocalDate.now().plusYears(1)));

            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    public static List<Student> findAllStudent() {
        List<Student> studentList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    SELECT * FROM student
                    """;
            var pstm = con.prepareStatement(sql);

            var rs = pstm.executeQuery();

            while (rs.next()) {
                var student = new Student();

                student.setCard_id(rs.getInt("card_id"));
                student.setName(rs.getString("name"));
                student.setRollNumber(rs.getInt("roll_no"));
                student.setYear(rs.getString("year"));
                student.setAcademicYear(rs.getString("academic_year"));
                student.setCreatedDate(LocalDate.parse(rs.getString("created_date")));
                student.setExpiredDate(LocalDate.parse(rs.getString("expired_date")));

                studentList.add(student);
            }

        } catch (Exception e) {

        }

        return studentList;
    }

    public static void editStudent(Student student) throws Exception {

        try (var con = MyConnection.createConnection()) {
            var sql = "UPDATE student SET name = ?, roll_no = ?, year = ?, academic_year = ? WHERE card_id = ?";
            var pstm = con.prepareStatement(sql);

            pstm.setString(1, student.getName());
            pstm.setInt(2, student.getRollNumber());
            pstm.setString(3, student.getYear());
            pstm.setString(4, student.getAcademicYear());
            pstm.setInt(5, student.getCard_id());

            pstm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateTimeStudent(Student student) throws Exception {
        try (var con = MyConnection.createConnection()) {
            var sql = "UPDATE student SET name = ?, roll_no = ?, year = ?, academic_year = ?, created_date = ?, expired_date = ? WHERE card_id = ?";
            var pstm = con.prepareStatement(sql);

            pstm.setString(1, student.getName());
            pstm.setInt(2, student.getRollNumber());
            pstm.setString(3, student.getYear());
            pstm.setString(4, student.getAcademicYear());
            pstm.setDate(5, Date.valueOf(student.getCreatedDate()));
            pstm.setDate(6, Date.valueOf(student.getExpiredDate()));

            pstm.setInt(7, student.getCard_id());

            pstm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Transaction> findAllTransaction() {
        List<Transaction> transactionList = new ArrayList<>();

        try (var con = MyConnection.createConnection()) {

            var sql = """
                    SELECT t.id, s.card_id, b.code, t.borrow_date, t.due_date, t.fees ,a.id 'admin_id'
                    FROM book b, admin a, student s, transaction t
                    WHERE t.card_id = s.card_id AND
                    t.book_id = b.code AND
                    t.admin_id = a.id
                    """;
            var prepareStatement = con.prepareStatement(sql);

            var rs = prepareStatement.executeQuery();

            while (rs.next()) {
                var transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.getStudent().setCard_id(rs.getInt("card_id"));
                transaction.getBook().setCode(rs.getInt("code"));
                transaction.setBorrowDate(LocalDate.parse(rs.getString("borrow_date")));
                transaction.setDueDate(LocalDate.parse(rs.getString("due_date")));
                transaction.setFees(rs.getInt("fees"));
                transaction.getAdmin().setId(rs.getInt("admin_id"));

                transactionList.add(transaction);
            }

        } catch (Exception e) {

        }

        return transactionList;
    }

    public static void borrowBook(String bookId, String cardId) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "INSERT INTO transaction(card_id, book_id, borrow_date, due_date, fees, admin_id) VALUES(?, ?, ?, ?, ?, ?)";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setInt(1, Integer.parseInt(cardId));
            prepareStatement.setInt(2, Integer.parseInt(bookId));
            prepareStatement.setDate(3, Date.valueOf(LocalDate.now()));
            prepareStatement.setDate(4, Date.valueOf(LocalDate.now().plusDays(7)));
            prepareStatement.setInt(5, 500);
            prepareStatement.setInt(6, LibraryApplication.loginAdmin.getId());

            prepareStatement.executeUpdate();



        } catch (Exception e) {
            throw e;
        }

    }

    public static void availableBookBorrow(Book book) {
        try (var con = MyConnection.createConnection()) {

            var sql = "UPDATE book SET available = ? WHERE code = ?";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setBoolean(1, book.getAvailable());
            prepareStatement.setInt(2, book.getCode());

            prepareStatement.executeUpdate();

        } catch (Exception e) {

        }
    }

    public static void returnBook(String bookId) throws Exception {

        try (var con = MyConnection.createConnection()) {

            var sql = "DELETE FROM transaction WHERE book_id = ?";
            var prepareStatement = con.prepareStatement(sql);

            prepareStatement.setInt(1, Integer.parseInt(bookId));
            prepareStatement.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }
}
