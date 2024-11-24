-- Delete existing data
DELETE FROM loans;
DELETE FROM members;
DELETE FROM books;
DELETE FROM authors;

-- Seed authors
INSERT INTO authors (id, firstName, lastName, birthDate)
VALUES
    (1, 'Jane', 'Austen', 1610836800000),      -- 1775-12-16
    (2, 'George', 'Orwell', -2103016800000),  -- 1903-06-25
    (3, 'J.K.', 'Rowling', -1585432800000),   -- 1965-07-31
    (4, 'Mark', 'Twain', -3403668000000),     -- 1835-11-30
    (5, 'Agatha', 'Christie', -1980446400000);-- 1890-09-15

-- Seed books
INSERT INTO books (id, title, description, author_id, publishingDate)
VALUES
    (1, 'Pride and Prejudice', 'A classic novel', 1, -5291695200000),    -- 1813-01-28
    (2, 'Sense and Sensibility', 'Another Jane Austen classic', 1, -5396728800000), -- 1811-10-30
    (3, 'Emma', 'A novel about matchmaking', 1, -5109328800000),         -- 1815-12-23
    (4, 'Mansfield Park', 'Another popular novel', 1, -5190928800000),   -- 1814-07-09
    (5, 'Northanger Abbey', 'A parody of Gothic novels', 1, -5061328800000), -- 1817-12-01

    (6, '1984', 'Dystopian fiction', 2, -787432880000),                 -- 1949-06-08
    (7, 'Animal Farm', 'Allegorical novella', 2, -791295600000),        -- 1945-08-17
    (8, 'Homage to Catalonia', 'Memoir', 2, -1128962400000),            -- 1938-04-25
    (9, 'Coming Up for Air', 'Pre-war novel', 2, -949700400000),        -- 1939-06-12
    (10, 'Down and Out in Paris and London', 'Autobiography', 2, -1162106400000), -- 1933-01-09

    (11, 'Harry Potter and the Philosopher''s Stone', 'Fantasy novel', 3, -868432800000), -- 1997-06-26
    (12, 'Harry Potter and the Chamber of Secrets', 'Second HP novel', 3, -831290400000), -- 1998-07-02
    (13, 'Harry Potter and the Prisoner of Azkaban', 'Third HP novel', 3, -794304000000), -- 1999-07-08
    (14, 'Harry Potter and the Goblet of Fire', 'Fourth HP novel', 3, -756940800000),     -- 2000-07-08
    (15, 'Harry Potter and the Order of the Phoenix', 'Fifth HP novel', 3, -719577600000);-- 2003-06-21

-- Seed members
INSERT INTO members (id, email, name, joinedAt)
VALUES
    (1, 'john.doe@example.com', 'John Doe', 344608800000),    -- 1980-11-12
    (2, 'jane.smith@example.com', 'Jane Smith', 462384000000), -- 1984-09-01
    (3, 'alice.johnson@example.com', 'Alice Johnson', 613584000000), -- 1989-06-12
    (4, 'bob.brown@example.com', 'Bob Brown', 262483200000),    -- 1978-04-01
    (5, 'charlie.wilson@example.com', 'Charlie Wilson', 294969600000), -- 1979-05-10
    (6, 'emma.clark@example.com', 'Emma Clark', 325036800000),   -- 1980-01-01
    (7, 'oliver.garcia@example.com', 'Oliver Garcia', 507235200000),-- 1985-01-10
    (8, 'sophia.martinez@example.com', 'Sophia Martinez', 428428800000),-- 1983-08-19
    (9, 'liam.lee@example.com', 'Liam Lee', 323692800000),     -- 1980-12-19
    (10, 'mia.white@example.com', 'Mia White', 502857600000);  -- 1985-02-19

-- Seed loans
INSERT INTO loans (id, book_id, member_id, loanedAt, returnDate)
VALUES
    (1, 1, 1, 1704048000000, 1704768000000), -- Loaned Pride and Prejudice to John Doe (2024-01-01 to 2024-01-08)
    (2, 6, 2, 1704048000000, 1705219200000), -- Loaned 1984 to Jane Smith (2024-01-01 to 2024-01-15)
    (3, 3, 5, 1704768000000, 1705382400000), -- Loaned Emma to Charlie Wilson (2024-01-08 to 2024-01-15)
    (4, 12, 8, 1705382400000, 1705996800000),-- Loaned HP Chamber of Secrets to Sophia Martinez (2024-01-15 to 2024-01-22)
    (5, 11, 9, 1704048000000, 1704768000000);-- Loaned HP Philosopher's Stone to Liam Lee (2024-01-01 to 2024-01-08)