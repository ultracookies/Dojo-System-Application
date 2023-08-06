DROP TABLE "Student" IF EXISTS;
DROP TABLE "Address" IF EXISTS;

CREATE TABLE "Address" (
    "AddressID" INTEGER IDENTITY PRIMARY KEY,
    "Street" VARCHAR(50),
    "City" VARCHAR(30),
    "Zipcode" VARCHAR(20),
    "State" VARCHAR(30)
);

CREATE TABLE "Student" (
   "ID" INTEGER IDENTITY PRIMARY KEY,
   "CustomID" VARCHAR(50) NOT NULL,
   "FirstName" VARCHAR(50) NOT NULL,
   "MiddleName" VARCHAR(50),
   "LastName" VARCHAR(50) NOT NULL,
   "Sex" CHAR NOT NULL,
   "Rank" VARCHAR(30) NOT NULL,
   "BirthDate" VARCHAR(10) NOT NULL,
   "Age" VARCHAR(20),
   "Height" VARCHAR(30),
   "Weight" VARCHAR(30),
   "DateBegan" VARCHAR(50),
   "PhoneNumber" VARCHAR(30),
   "AddressID" INTEGER,
       FOREIGN KEY ("AddressID") REFERENCES "Address" ("AddressID")
);
