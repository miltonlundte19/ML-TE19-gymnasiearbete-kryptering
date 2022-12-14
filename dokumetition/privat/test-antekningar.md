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
    { ser key, decrypt }

Start time:
1383752433233700  
File:
C:\code\krypt-test-fils\lorum-ut-test-crypt.txt  
Tiden aes crypteringen började:
1383752470810100  
Den krypterade filen är har:
C:\code\krypt-test-fils\lorum-ut-de-test-ceypt.txt  

Blev sama text i slutet.

