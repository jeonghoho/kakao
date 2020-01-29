
CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(20),
    join_date timestamp
);

CREATE TABLE board (
    id INTEGER auto_increment PRIMARY KEY,
    name VARCHAR(20),
    password VARCHAR(255),
    title VARCHAR(255),
    content CLOB,
    read_count INTEGER,
    write_date timestamp,
    update_date timestamp
);
