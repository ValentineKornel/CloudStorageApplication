CREATE TABLE IF NOT EXISTS USERS (
  userId INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR(100),
  password VARCHAR(50),
  firstName VARCHAR(20),
  lastName VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    isPublic TINYINT(1),
    filename VARCHAR(100),
    contentType VARCHAR(80),
    fileSize VARCHAR(20),
    userId INT,
    ownerUsername VARCHAR(20),
    fileData LONGBLOB,
    foreign key (userId) references USERS(userId)
);
