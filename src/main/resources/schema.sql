DROP TABLE "Student" IF EXISTS;

CREATE TABLE "Student"(
   "ID" INTEGER IDENTITY PRIMARY KEY,
   "FirstName" VARCHAR(50) NOT NULL,
   "LastName" VARCHAR(50) NOT NULL,
   "Rank" VARCHAR(50) NOT NULL
);