# nerskrivning av test


---
## resultat

1. String  
    { randomKey, encrypting }

Start time: 
3409678744532800  
String:
deta är ett test av först setings modelen och senan crypteringen  
Tiden crypteringen började:
3409678785841500  
Den krypterade strengen blev:
e/6xkWVxsZ44P90jxqRoAkKd564GS67iryZsDdqUYUHMxsXj0GH1nJDxkMygackMKGT8iqNHeFi+wURWRGxcmNUq8UvEZVH2xSOojiU3cYQ=  


2. File  
    { randomKey, encrypting }

Start time:
3462316086664300

Glömt .fluch() med fungerade

3. String (aes)  
    { set Key, encrypting }

Start time:
780410955328800  
String:
test om hur olika iv andrar resultatet  
Tiden aes crypteringen började:
780411016576400  
den krypterade strengen blev:
OV8QRNqNcNgQNvFjKmUDPf612sBXmPCai1qLVICQ5V0Yu//u35UkNbvRYfVAMERX  

4. String (aes)  
      { set Key, encrypting }

Start time:
780725419411400  
String:
test om hur olika iv andrar resultatet  
Tiden aes crypteringen började:
780725469997400  
Den krypterade strengen blev:
8bVyCTgmPHdkDgK8Hxp8WZ6EOKYxg7SxOe6sEO6uRCzEq8qQFOwk9wnVLHHspg7M  

5. String (aes)  
   { set Key, encrypting }

Start time:
780848090677600  
String:
test om hur olika iv andrar resultatet  
Tiden aes crypteringen började:
780848136062200  
Den krypterade strengen blev:
xOaHUFimD3B4JoV7DUbarXJ8Sej5wr2bYoBsb0lzij0UfTA/GJa1ipyPUDeYXlwJ  

6. String (aes)  
    { set Key, decrypt }

Start time:
1380666153978200  
String:
xOaHUFimD3B4JoV7DUbarXJ8Sej5wr2bYoBsb0lzij0UfTA/GJa1ipyPUDeYXlwJ  
Tiden aes crypteringen började:
1380666194479300  
Den krypterade strengen blev:
test om hur olika iv andrar resultatet  

7. String (aes)  
    { set Kye, decrypt }

Start time:
1381022794581200  
String:
xOaHUFimD3B4JoV7DUbarXJ8Sej5wr2bYoBsb0lzij0UfTA/GJa1ipyPUDeYXlwJ  
Tiden aes crypteringen började:
1381022826973400  
Den krypterade strengen blev:
�'fy�}N��$&N�na iv andrar resultatet  

Visar att iv måste vara sama vid inkryptering och dekryptering.

8. String (aes)  
    { set Key, decrypt }

Start time:
1381464470081500  
String:
xOaHUFimD3B4JoV7DUbarXJ8Sej5wr2bYoBsb0lzij0UfTA/GJa1ipyPUDeYXlwJ  
Tiden aes crypteringen började:
1381464506977700  
Den krypterade strengen blev:
T�X���!3���kV�a iv andrar resultatet  

Bekräftar enu mer att iv måste vara samma.

9. File (aes)  
    { set Key, encrypt }

Start time:
1383465711868900  
File:
C:\code\krypt-test-fils\lorum-test-crypt.txt  
Tiden aes crypteringen började:
1383465757353500  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  

Serkar har fungerat (texten var Lorem ipsum)

10. File (aes)  
    { set key, decrypt }

Start time:
1383752433233700  
File:
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  
Tiden aes crypteringen började:
1383752470810100  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-de-test-ceypt.txt  

Blev sama text i slutet.

11. String (res)  
    { set key, encrypt, pri }

Start time:
1539370122152400  
String:
Första riktiga testet av rsa krypteringen  
Tiden res crypteringen började:
1539370177511900  
Den krypterade strengen blev:
\d
�p�r���K�����M��5ss}4r��]~���1Ú���ȵ�ذ�xM�����_�<=]��� L
��_S�=����ES��'�k:��8�/0ᮞ�v��~%��q�3��0�����{���~�o�n�%ʉ�@d�49.�#��.˅z�EI3��S��ĸ�XS[�L��f$;-D��G��81X�r1GF��� �o�1��ћu��>췿)��I��,��.h@�/X�ɒ
����)����B  

12. String (res)  
    { set key, decrypt, pub }

Start time:
1539775492746600  
String:
\d
�p�r���K�����M��5ss}4r��]~���1Ú���ȵ�ذ�xM�����_�<=]��� L
��_S�=����ES��'�k:��8�/0ᮞ�v��~%��q�3��0�����{���~�o�n�%ʉ�@d�49.�#��.˅z�EI3��S��ĸ�XS[�L��f$;-D��G��81X�r1GF��� �o�1��ћu��>췿)��I��,��.h@�/X�ɒ
����)����B  
Tiden res crypteringen började:
1539775535008800  
Den krypterade strengen blev:

Jag måste enkoda den krypterade stringen för att kunna få ut en korrekt string i slutet  
(Illegal bsse64 character 5c)


13. String (res)  
    { set key, encrypt, pri }

Start time:
1541124608845500  
String:
Andra testet med res string  
Tiden res crypteringen började:
1541124647320600  
Den krypterade strengen blev:
JUcGbYc/Qfe8dTXZ6J6Y6DvUBOYq9OkjFfjLVj6qezoMtmOusor4MFW5/Z2dhRh30uzNyRvVoIEclEZkesg/dWwtplZgKq2XUktYedxx2nVO1Dk5AuU6vXISXsEzurMMamup8/6Dv0j67rjwwFNNsAIyl3QgT6+VO5kW35LncJcUom87dk0+tF2KaJIpJxJvw3FD0V03I9R9eb75RF7MaSRtwVpdYicneEW3EOoTzkMF3j3hfJdXmWqTWtwGLd73vODGAmIrqukRuWluxst+X9ugyJYXpd9mis0Wez9Wk4xr7drP0cFgnmvtYjc/TqoD7EZ2mfi6qg3nxlo3NXBF5A==  

14. String (res)  
    { set key, decrypt, pub }

Start time:
1541293059436800  
String:
JUcGbYc/Qfe8dTXZ6J6Y6DvUBOYq9OkjFfjLVj6qezoMtmOusor4MFW5/Z2dhRh30uzNyRvVoIEclEZkesg/dWwtplZgKq2XUktYedxx2nVO1Dk5AuU6vXISXsEzurMMamup8/6Dv0j67rjwwFNNsAIyl3QgT6+VO5kW35LncJcUom87dk0+tF2KaJIpJxJvw3FD0V03I9R9eb75RF7MaSRtwVpdYicneEW3EOoTzkMF3j3hfJdXmWqTWtwGLd73vODGAmIrqukRuWluxst+X9ugyJYXpd9mis0Wez9Wk4xr7drP0cFgnmvtYjc/TqoD7EZ2mfi6qg3nxlo3NXBF5A==  
Tiden res crypteringen började:
1541293099468600  
Den krypterade strengen blev:
Andra testet med res string  

String krypteringen verkar fungera

15. String (res)  
    { set key, encrypt, pub }

Start time:
1541637323935700  
String:
Et test av en lång string med numer 554994 och några spesialteken %&&¨¨ och lite mer ord. hej jag heter lorum och undrar om du är ipsum och lorum ipsum och så vidare och vidare  
Tiden res crypteringen började:
1541637363426500  
Den krypterade strengen blev:
pmhxRYIuU5NddM4KNlhX0b0g3ZSuT0KtfLV6jjGtRKDzEjb/t+A4ws1YSfX/gF7FYxS1rQmEdiqGC8z6XBLon8KFn/ZZdlQ1gh1q4hjNkTDSs558unV3VWLDZ0ur47omU8GAuXzmaCBEUD58GnxU6VVn+75DIjFJ2InYqPVli9CYdHOZG6AVL4RD4cfgWs86NZP7Y3qhIj76o6QfyTFf/WbpZZiAB4g19dXZ+Uz/kX8ii/5EelMGvBKsiz71WgWG2iFH+VM5YwoGnjMWqDDSVE+BCs++aYINHsU7OfA7X8HDIWRGtVFuGtuHjpYLRZn02TcVA6Y6ty02TES4cnh6Cw==  

Test med en liten längre string och pub för att encryptera

16. String (res)  
    { set key, decrypt, pri }

Start time:
1541864320017900  
String:
pmhxRYIuU5NddM4KNlhX0b0g3ZSuT0KtfLV6jjGtRKDzEjb/t+A4ws1YSfX/gF7FYxS1rQmEdiqGC8z6XBLon8KFn/ZZdlQ1gh1q4hjNkTDSs558unV3VWLDZ0ur47omU8GAuXzmaCBEUD58GnxU6VVn+75DIjFJ2InYqPVli9CYdHOZG6AVL4RD4cfgWs86NZP7Y3qhIj76o6QfyTFf/WbpZZiAB4g19dXZ+Uz/kX8ii/5EelMGvBKsiz71WgWG2iFH+VM5YwoGnjMWqDDSVE+BCs++aYINHsU7OfA7X8HDIWRGtVFuGtuHjpYLRZn02TcVA6Y6ty02TES4cnh6Cw==  
Tiden res crypteringen började:
1541864358094500  
Den krypterade strengen blev:
Et test av en lång string med numer 554994 och några spesialteken %&&¨¨ och lite mer ord. hej jag heter lorum och undrar om du är ipsum och lorum ipsum och så vidare och vidare  

17. File (res)  
    { set key, encrypt, pub }

Start time:
1542178560953900  
File: C:\code\krypt-test-fils\lorum-test-crypt.txt  
Tiden res crypteringen började:
1542178600273000  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  

Mislykades eftersom att filen var för stor

18. File (res)  
    { set key, encrypt, pub }

Mislykad eftersom filen var för stor

19. File (res)  
    { set key, encrypt, pub }

Start time:
1548623071240200  
File: 
C:\code\krypt-test-fils\lorum-test-crypt.txt  
Tiden res crypteringen började:
1548623108329900  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  

20. File (res)  
    { set key, decrypt, pri }

Start time:
1549246868086500  
File: 
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  
Tiden res crypteringen började:
1549246923622600  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-de-test-ceypt.txt  

---

21. File (aes)  
    { set key, encrypt }

Start time:
2023-05-10T12:44:12.231125100  
File: 
C:\code\test av programet\testtext.txt  
Starlet: 1 GB  
Tiden aes krypteringen började:
2023-05-10T12:44:17.425138700  
Den krypterade filen är har: 
null  
Krypteringen kördes: 
8  
Första krypteringen slutade:
12:44:21.543264700  
Krypteringen slutade:
2023-05-10T12:45:14.584649  