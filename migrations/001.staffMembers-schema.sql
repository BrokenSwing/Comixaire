DROP TABLE IF EXISTS staffMembers;

CREATE TABLE staffMembers(
  staffMember_id INT GENERATED ALWAYS AS IDENTITY,
  staffMember_username VARCHAR(255) NOT NULL CONSTRAINT unique_username UNIQUE,
  staffMember_password VARCHAR(255) NOT NULL,
  staffMember_role VARCHAR(255) NOT NULL,
  PRIMARY KEY(staffMember_id)
);

INSERT INTO staffMembers(staffMember_username, staffMember_password, staffMember_role)
VALUES('Florent','Florent123','Admin'),
      ('Keven','Keven123','Employee'),
      ('Axel','Axel123','Employee'),
      ('Florine','Florine123','Employee');