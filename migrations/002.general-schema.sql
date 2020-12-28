DROP TABLE IF EXISTS staffMembers;
DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS subscriptions;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS libraryItems;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS CD;
DROP TABLE IF EXISTS DVD;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS returns;
DROP TABLE IF EXISTS fineType;
DROP TABLE IF EXISTS fine;

CREATE TABLE staffMembers(
    staffMember_id INT GENERATED ALWAYS AS IDENTITY,
    staffMember_username VARCHAR(255) NOT NULL CONSTRAINT unique_username UNIQUE,
    staffMember_password VARCHAR(255) NOT NULL,
    staffMember_role VARCHAR(255) NOT NULL,
    PRIMARY KEY(staffMember_id)
);

CREATE TABLE logs(
    log_id INT GENERATED ALWAYS AS IDENTITY,
    log_date TIMESTAMP NOT NULL,
    log_operationDetails VARCHAR(255) NOT NULL,
    log_operationType VARCHAR(255) NOT NULL,
    staffMember_id INT,
    PRIMARY KEY(log_id),
    CONSTRAINT fk_staffMember
        FOREIGN KEY(staffMember_id)
        REFERENCES staffMembers(staffMember_id)
        ON DELETE SET NULL
);

CREATE TABLE subscriptions(
    subscription_id INT GENERATED ALWAYS AS IDENTITY,
    subscription_from DATE NOT NULL,
    subscription_to DATE NOT NULL,
    PRIMARY KEY(subscription_id)
);

CREATE TABLE clients(
    client_id INT GENERATED ALWAYS AS IDENTITY,
    client_firstname VARCHAR(255) NOT NULL,
    client_lastname VARCHAR(255) NOT NULL,
    client_gender VARCHAR(255) NOT NULL,
    client_birthdate DATE NOT NULL,
    client_address VARCHAR(255) NOT NULL,
    client_cardID VARCHAR(255) NOT NULL CONSTRAINT unique_cardID UNIQUE,
    subscription_id INT,
    PRIMARY KEY(client_id),
    CONSTRAINT fk_subscription
        FOREIGN KEY(subscription_id)
        REFERENCES subscriptions(subscription_id)
        ON DELETE SET NULL
);

CREATE TABLE libraryItems(
    item_id INT GENERATED ALWAYS AS IDENTITY,
    item_title VARCHAR(255) NOT NULL,
    item_condition VARCHAR(255) NOT NULL,
    item_location VARCHAR(255) NOT NULL,
    item_createdOn DATE NOT NULL,
    item_releasedOn DATE NOT NULL,
    item_bookings INT[],
    item_available BOOLEAN NOT NULL,
    item_categories VARCHAR(255)[] NOT NULL,
    PRIMARY KEY(item_id)
);

CREATE TABLE books(
    item_id INT NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    book_ISBN VARCHAR(255) NOT NULL,
    book_publisher VARCHAR(255) NOT NULL,
    book_pagesCount INT NOT NULL,
    PRIMARY KEY(item_id),
    CONSTRAINT fk_libraryItemBook
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE games(
    item_id INT NOT NULL,
    game_publisher VARCHAR(255) NOT NULL,
    game_minPlayers INT NOT NULL,
    game_maxPlayers INT NOT NULL,
    game_minAge INT NOT NULL,
    game_contentInventory TEXT NOT NULL,
    PRIMARY KEY(item_id),
    CONSTRAINT fk_libraryItemGame
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE CD(
    item_id INT NOT NULL,
    CD_artist VARCHAR(255) NOT NULL,
    CD_duration INT NOT NULL,
    PRIMARY KEY(item_id),
    CONSTRAINT fk_libraryItemCD
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE DVD(
    item_id INT NOT NULL,
    DVD_duration INT NOT NULL,
    DVD_producer VARCHAR(255) NOT NULL,
    DVD_casting VARCHAR(255)[] NOT NULL,
    PRIMARY KEY(item_id),
    CONSTRAINT fk_libraryItemCD
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE rating(
    client_id INT NOT NULL,
    item_id INT NOT NULL,
    note INT NOT NULL,
    PRIMARY KEY(client_id, item_id),
    CONSTRAINT fk_clientRate
        FOREIGN KEY(client_id)
        REFERENCES clients(client_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_libraryItemRate
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE loans(
    loan_id INT GENERATED ALWAYS AS IDENTITY,
    loan_from DATE NOT NULL,
    loan_to DATE NOT NULL,
    client_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY(loan_id),
    CONSTRAINT fk_clientLoan
        FOREIGN KEY(client_id)
        REFERENCES clients(client_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_libraryItemLoan
        FOREIGN KEY(item_id)
        REFERENCES libraryItems(item_id)
        ON DELETE CASCADE
);

CREATE TABLE returns(
    return_date DATE NOT NULL,
    loan_id INT NOT NULL,
    PRIMARY KEY(loan_id),
    CONSTRAINT fk_loan
        FOREIGN KEY(loan_id)
        REFERENCES loans(loan_id)
        ON DELETE CASCADE
);

CREATE TABLE fineType(
    fineType_id INT GENERATED ALWAYS AS IDENTITY,
    fineType_label VARCHAR(255) NOT NULL,
    fineType_price INT NOT NULL,
    PRIMARY KEY(fineType_id)
);

CREATE TABLE fine(
    return_id INT NOT NULL,
    fineType_id INT NOT NULL,
    paid BOOLEAN NOT NULL,
    PRIMARY KEY(return_id, fineType_id),
    CONSTRAINT fk_return
        FOREIGN KEY(return_id)
        REFERENCES returns(return_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_fineType
        FOREIGN KEY(fineType_id)
        REFERENCES fineType(fineType_id)
        ON DELETE CASCADE
);