# Department of Computing Sciences
## The College at Brockport, State University of New York
### CSC 427: Software Systems Engineering (Fall 2017)
### Term Project
#### HIGH-LEVEL PROBLEM DESCRIPTION OF THE COLLEGE AT BROCKPORT PROFESSINAL CLOTHES CLOSET SYSTEM

The Career Services Office at the College at Brockport provides a service to the students who need professional (business) clothing for interviews, etc. This service is provided through donated clothing to the Career Services office. An item of clothing donated is recorded in the inventory of the professional clothes closet maintained by the office. These items are then taken by registered Brockport students who might need them, after they find a suitable item. Alternatively, if no such item is currently available, the student is allowed to enter a request for an item of clothing. This will be recorded by the system. Once an item of clothing is donated, the administrator (user) of the system will go through pending requests, and see if any of them can be fulfilled by any of the items in inventory. If a suitable match is found, the requester will be contacted by Career Services using phone/email and asked to come by and pick up the item.
Each item of clothing that comes in as a donation is barcoded. The barcode is a sequence of digits – the first digit will be a 0 or 1 (0 – to indicate it is an item intended for men, and 1 – to indicate it is an item intended for women). The rest of the digits is a combination of clothing article type (pant/pant suit/shirt/etc.), color and a sequence number. See the examples in the data model below for illustrations.
The system should allow Career Services personnel to carry out various tasks, which include the following:


1. Add an article type to the system (Note: each article type will be recorded with the data indicated in the data model for the Article type rolodex/table below. The default status of the article type added is “Active” – the user should not be asked for this value, although he should be asked for the other values (other than the Id, of course)).
2. Modify information about an article type in the system. For this feature, the user (administrator) should be allowed to search for the article type based on the alpha code and/or part of the description. Once a visual table of article types matching the search criteria are provided by the system, the user should be allowed to select one of them and modify the following – description, barcode prefix, and alpha code. The id can never be modified.
3. Delete an article type from the system. For this feature, the article type will be searched for exactly as in (2) above, and selected by the user. The user will then simply be asked to confirm the delete of the article type. The article type is not physically deleted from the system – its Status value is simply marked as “Inactive”.
4. Add a color to the system. Note that each color will be added to the system with the data for the color indicated in the data model below. As for article types, the user should not be asked for the Status and id (which default to “Active” and is auto generated, respectively).
5. Modify information about a color in the system. For this feature, the user will be allowed to search for colors using the alpha code and/or the description of the color.  In other words, the feature proceeds in a manner very similar to the modify article type feature.
6. Delete a color from the system. This feature’s functionality proceeds just like the delete feature for article types.
7. Add a donated item of clothing to the system. This feature adds the donated item of clothing to the Inventory rolodex. Before the user goes to the computer, he should attach a barcode to the item of clothing – the computerized system does not generate the barcode; it is up to the user to follow the desired barcode generation rules and attach a barcode to the item of clothing. The computerized part of this feature begins by the user scanning in the barcode into the appropriate screen. Once the barcode is scanned in, the system should attempt to parse the barcode. If the barcode is exactly 8 digits long, then the system should use the first digit to record a gender (M or W), the next two digits to know the article type (using the barcode prefix of the article type rolodex), and the next two digits to know the primary color – color 1 (using the barcode prefix of the color rolodex). It should then convey these choices to the user, who can change them if they so desire. The user provides other information – this includes size, color 2, brand name and notes. The default Status of the item of clothing is “Donated” – the user should not be asked for this value. If known, the user should provide the donor’s first and last name, phone number and e-mail. The system should compute the date donated as today’s date (which the computer can do), and be sure not to ask the user for it. After all this is done, the user should ask the system to save this item of clothing, in which case the system stores it as a new index card into the rolodex (i.e., as a new row in the database table).
8. Modify information about a donated item of clothing in the system. This feature requires that the user scan in (or enter using the keyboard), the barcode of the item of clothing. Once this is done, the system retrieves the item of clothing from the rolodex/table and provides the data to the user. The user then changes the data in the index card/row as needed, and tells the system to re-file the item of clothing (NOTE: we have had extensive discussion on filing/re-filing into rolodexes in class)
9. Remove an item of clothing in the system. The item of clothing will be retrieved using the barcode, and its Status will be set to “Removed” and re-filed into the system.
10. Check out an item of clothing from the system. This feature assumes that the customer (student) has brought the item of clothing to the user (i.e., to Career Services personnel at the desk). The barcode of the item is available. The system retrieves the item of clothing using this barcode and asks the customer (student) for their netid, first name and last name. This information is then added to the index card corresponding to the item and the date taken value is computed as today’s date (NOTE: again, the user should not be asked for this date). The Status is set to the value “Received”. The system then re-files this index card.
11. Log a request into the system. This feature is executed when a customer (student) cannot find a suitable item of clothing in the closet, and would like to keep a request on file that will (hopefully) be fulfilled when a suitable item comes in. The user takes all the desired information from the customer (student), including a suitable phone number (if the student gives one). The system will record the request’s status as “Pending”, compute the “Request Made Date” value as today’s date, and file it into the appropriate rolodex.
12. Fulfil a request. This feature is initiated by the user – i.e., by Career Services personnel. When this feature is started, the user is presented with a list of all pending requests, ordered by “Request Made Date”, from earliest to latest. The user will select a request and ask the system to see if it can be fulfilled. The system will check the “Inventory” rolodex/table to see if there are any clothing items that match the following:
    1. Article type in inventory table matches the article type in the request
    2. Gender of the article type in the request
    3. Size in inventory table matches the size in the request
    4. The status in the inventory table is “Donated”
    
    All these matching items will then be shown to the user, including the barcode of the matching items. The user can select one of the matching items and offer it to the customer (student). The customer (student) can tell the user if they agree to take the item (ignoring, possibly, that the color(s) don’t match, and the brand does not match their desired brand). If this happens, then the user tells the system that the item of clothing with the desired barcode fulfilled this request. In this case, the system changes the Status of the request to “Fulfilled”, records this barcode as the Fulfil Item Barcode, and the current date as the “Request Fulfilled Date” (again, note that the user is not to be asked to provide these dates). The system then re-files the request index card into the rolodex. Thereafter, the system must use the chosen barcode to retrieve the index card for the appropriate item of clothing from the Inventory rolodex, change the Status of this item of clothing to “Received”, record the requester’s last name, first name and netid as the receiver’s netid, last name and first name in this index card, record the current date as date taken and re-file the index card into the Inventory rolodex.
13. Remove a request. This feature requires the system to provide the user with a list of all pending requests, ordered by date received. The user selects a request to remove, and that request’s status is changed to “Removed”
14. List all available inventory. This feature requires the system to provide the user with a list of all clothing items in inventory with status “Donated”, ordered by date received.
These are the overall requirements – details of additional functionality will be provided later, if necessary, by your instructor, after consultation with the customer.

#### DATA MODEL FOR THE PROFESSIONAL CLOTHES CLOSET SYSTEM
##### Rolodex: Article Type
Fields:
1. Id (Integer) (Auto-generated PK)
2. Description (Text)
3. Barcode Prefix (Text) - Unique
4. Alpha code (Text)
5. Status (Active/Inactive – Default: “Active”)

Examples:

| Id | Description | Barcode Prefix | Alpha code | Status |
|---:|:-----------:|:--------------:|:----------:|:------:|
|  1 | Pant Suit   | 01             | PS         | Active |
|  2 | Skirt Suit  | 02             | SS         | Active |
|  3 | Blazer      | 03             | BL         | Active |
|  4 | Dress       | 04             | D          | Active |
|  5 | Shoe        | 05             | SH         | Active |
|  6 | Shirt       | 06             | ST         | Active |
|  7 | 3P Pant Suit| 07             | 3PS        | Active |
|  8 | Pants       | 08             | P          | Active |
|  9 | Trench      | 09             | TR         | Active |
| 10 | Top         | 10             | TP         | Active |
| 11 | Belt        | 11             | BE         | Active |
| 12 | Suit        | 12             | SU         | Active |
| 13 | Scarf       | 13             | SC         | Active |
| 14 | Coat        | 14             | C          | Active |
| 15 | Sweater     | 15             | SW         | Active |
| 16 | Jacket      | 16             | JK         | Active |
| 17 | Skirt       | 17             | SK         | Active |
| 18 | Vest        | 18             | V          | Active |
| 19 | Tie         | 19             | T          | Active |

##### Rolodex: Color
**Fields:**
1. Id (Integer) (Auto-generated PK)
2. Description (Text)
3. Barcode Prefix (Text) - Unique
4. Alpha Code (Text)
5. Status (Active/Inactive – Default: “Active”)

Examples:

| Id | Description | Barcode Prefix | Alpha code | Status |
|---:|:-----------:|:--------------:|:----------:|:------:|
|  1 | Black       | 01             | BK         | Active |
|  2 | Blue        | 02             | BL         | Active |
|  3 | Brown       | 03             | BR         | Active |
|  4 | Beige       | 04             | BE         | Active |
|  5 | Grey        | 05             | GR         | Active |
|  6 | White       | 06             | WH         | Active |
|  7 | Pink        | 07             | P          | Active |
|  8 | Red         | 08             | R          | Active |
|  9 | Green       | 09             | G          | Active |
| 10 | Cream       | 10             | CR         | Active |
| 11 | Teal        | 11             | TE         | Active |
| 12 | Navy        | 12             | NV         | Active |
| 13 | Purple      | 13             | PR         | Active |
| 14 | Maroon      | 14             | M          | Active |
| 15 | Tan         | 15             | TA         | Active |
| 16 | Orange      | 16             | OR         | Active |
| 17 | Yellow      | 17             | Y          | Active |
| 18 | 4+ Colors   | 18             | 4PC        | Active |
| 19 | Print       | 19             | PN         | Active |
| 20 | Pin Stripes | 20             | PS         | Active |
| 21 | Patterns    | 21             | PA         | Active |

##### Rolodex: Inventory
**Fields:**
1. Barcode (Text) (PK)
2. Gender (Text)
3. Size (Text)
4. Article Type (Integer) – FK to Article Type rolodex/table
5. Color1 (Integer) – FK to Color rolodex/table
6. Color2 (Integer) – FK to Color rolodex/table (can be NULL or “”)
7. Brand (Text)
8. Notes (Text)
9. Status (“Donated”, “Received”, “Removed” – default is “Donated”)
10. Donor Lastname (Text)
11. Donor Firstname (Text)
12. Donor phone (Text)
13. Donor email (Text)
14. Receiver Netid (Text)
15. Receiver Lastname (Text)
16. Receiver Firstname (Text)
17. Date donated (Text)
18. Date taken (Text)
    
Examples:

| Barcode  | Gender | Size | Art. Type | Color 1 | Color 2 | Brand                 | Notes | Status   | Donor Last Name | Donor First Name | Donor Phone | Donor Email | Receiver Netid | Receiver Last Name | Receiver First Name | Date Donated | Date Taken |
|----------|--------|------|-----------|---------|---------|-----------------------|-------|----------|-----------------|------------------|-------------|-------------|----------------|--------------------|---------------------|--------------|------------|
| 10801001 | W      | 2    | 08        | 01      | 20      | Old Navy              |       | Received | Test | 585-3952234  | ..@..  | astandis  | Standish  | Adam  | 2017-05-08  | 2017-05-09
| 10803001 | W      | 4    | 08        | 03      | 00      | Loft                  |       | Donated  | Test | 585-395….  | ..@..  | -  | -  | -  | 2017-05-06  | -
| 11703001 | W      | 6    | 17        | 03      | 00      | Express Design Studio |       | Donated  | Test | 585-395….  | ..@..  | -  | -  | -  | 2017-05-04  | -
| 11701001 | W      | 6    | 17        | 01      | 00      | Christopher & Banks   |       | Received | Test | 585-395….  | ..@..  | jwesley  | Wesley  | Jill  | 2017-05-03  | 2017-05-10
| 10204001 | W      | 4    | 02        | 04      | 00      | Talbots               |       | Donated  | Test | 585-395….  | ..@..  | -  | -  | -  | 2017-05-02  | -
| 10601001 | W      | 16   | 06        | 01      | 06      | Madison Michelle      | Comes with black tank top  | Received  | Test | 585-395….  | ..@..  | jalston  | Alston  | Jeff  | 2017-05-01  | 2017-05-08

##### Rolodex: Clothing Request
**Fields:**
1. Id (Integer) (Auto-generated PK)
2. Requester Netid (Text)
3. Requester Phone (Text)
4. Requester Lastname (Text)
5. Requester Firstname (Text)
6. Requested Gender (Text)
7. Requested Article Type (Integer) – FK to Article Type rolodex/table
8. Requested Color1 (Integer) – FK to Color rolodex/table
9. Requested Color2 (Integer) – FK to Color rolodex/table (can be NULL or “”)
10. Requested Size (Text)
11. Requested Brand (Text)
12. Request Status (“Pending”, “Fulfilled”, “Removed”)
13. Fulfil Item Barcode (Text) – FK to Inventory rolodex/table
14. Request Made Date (Text)
15. Request Fulfilled Date (Text)

        
Examples:      

| Id | Requester Netid |  T   | Requested Gender | Requested Article Type |  T | Request Status  |Fulfil Item Bar code  | Date    |
|---:|:---------------:|:---:|:----------------:|:----------------------:|---:|:---------------:|----------------------|---------|
|  1 | Jalston | … | M | 08 | ... | Pending | - | 2017… | -
| 2 | jwesley | … | W | 02 | … | Fulfilled | 10… | 2017… | 2017 …
| 3 | astandis | …. | M | 17 | .. | Removed | - | 2017 … | 2017 ….

