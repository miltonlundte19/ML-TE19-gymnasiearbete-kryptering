https://www.overleaf.com/read/gtjqtsprtctj - länk till dokumentet

---
### Frågor till magnus:
- hur måste jag göra om File för att det ska fungera i en jar  

---

### Abstract:
Skriv när resten är klart! Här står VAD som undersökts, kort om HUR det undersökts och VAD du kommit fram till.

### Introduktion: 
Varför valde du att undersöka detta område?

1. Vad använder man Java till?  
   Java är ett programspråk som används av företag och är populärt bland nybörjare. Programmet har blivit en global standard för utveckling och leverans av  mobila applikationer,  programvara för företag, inbäddade applikationer och har satt grunden för nästan alla typer av nätverksanslutna applikationer.


2. Hur kan det vara användbart att ta reda på det du undersöker i ditt arbete? Att ta reda på…  

### Syfte:
Syftet är att jämföra olika krypteringsalgoritmer i java för att ta reda på hur lång tid krypteringen tar och hur mycket resurser krypteringen tar av datorn.

### Frågeställning:
- Hur lång tid tar olika krypteringsalgoritmer att kryptera en fil?
- Hur mycket prestanda tar olika krypteringsalgoritmer att kryptera en fil?
- Hur påverkas de tidigare frågeställningarna av storleken på filen (och formatet)?
- Hur påverkas de tidigare frågeställningarna av olika hårdvara på datorn (bärbar mot stationär)?

### Bakgrund: 
2. Bakgrunden:  
  a) Välj vilka krypteringsalgoritmer som ska studeras och du kommer att använda.  
   (Symmetrisk kryptering, asymmetrisk kryptering, hybrid kryptering)  
  b) Förklara hur de fungerar (översiktligt först). Detta blir bakgrunden (Kan fördjupas mer senare)  
  c) Visa hur de kan användas i Java. Skapa program som ska användas i testet. Detta ska in i metoden.  

#### Förklara vad kryptering är (med historia)
När man krypterar så ändrade man innehållet av informationen så att man inte kan läsa vad informationen var från början. 
Kryptering används för att kunna säkert lagra och skicka information utan att någon kan stjäla eller ändra på informationen.  

#### (förklara vad nycklar är och nyckel generatorer är) - (senare i bakgrunden?)

#### förklara vad symmetrisk kryptering är (med olika algoritmer)
Symmetrisk kryptering är en typ av kryptering som använder en enda nyckel för att kryptera och dekryptera.
([Subrato Mondal](https://dzone.com/articles/security-implementation-of-hybrid-encryption-using) 2021)

Användaren [[nimma_shravan_kumar_reddy](https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/) 2021] från geeksforgeeks.org 
skriver att symmetrisk kryptering innebär att de inblandade parterna delar på en standardnyckel. Båda parterna måste förvara sin nyckel hemlig. 
“Till exempel, “A” kommer kryptera ett meddelande med en delad nyckel “K”, sedan kan B dekryptera det krypterade meddelandet bara med “K”, skriver 
([nimma_shravan_kumar_reddy](https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/), 2021).

#### förklara vad asymmetrisk kryptering är (med olika algoritmer)
([Subrato Mondal](https://dzone.com/articles/security-implementation-of-hybrid-encryption-using) 2021) 
skriver att icke symmetrisk kryptering har en publik och en privat nyckel för att göra samma sak som symmetrisk kryptering. 
Det gör så risken att fel part kan dekryptera datan försvinner. 
Tiden att kryptera datan med asymmetrisk kryptering växer proportionellt med storleken utav datan. 

([nimma_shravan_kumar_reddy](https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/), 2021) - (2022-02-08)  

För asymmetrisk kryptering eller publik/privat nyckelparskryptering används två matematiskt relaterade nycklar. 
Den ena nyckeln för att kryptera meddelandet och den andra för att dekryptera meddelandet.

Om “A” skapar ett nyckelpar som är publikt och privat och delar den publika nyckeln med alla, 
så kan “A” skicka ett meddelande till “B” som “A” har krypterat med sin privata nyckel. “B” använder “A”:s publika nyckel 
för att dekryptera meddelandet. För att skicka ett privat meddelande till “A” kan “B” använda “A”:s publika nyckel för att 
kryptera meddelandet. “A” använder sin privata nyckel för att sedan dekryptera meddelandet. 

([Jay Sridhar](https://www.novixys.com/blog/rsa-file-encryption-decryption-java/), 2017) - (2022-02-09)  
skriver att “Asymmetrisk krypering” står för att använda ett nyckelpar för kryptering. 
Nyckelparet har en nyckel som är publik och den andra är privat. 
För att dekryptera data som har blivit krypterad med den ena nyckeln kan bara den andra nyckeln användas för att dekryptera. 

#### förklara vad hybrid kryptering är (med olika algoritmer)
([Subrato Mondal](https://dzone.com/articles/security-implementation-of-hybrid-encryption-using) 2021)
Förklarar att hybridkryptering kombinerar de bästa utav symmetrisk kryptering och asymmetrisk kryptering. 
Det gör att krypteringen blir lika effektiv, men har också bra säkerhet.

Klienten krypterar datan med symmetrisk kryptering, med en hemlig nyckel. 
Sedan krypteras den hemliga nyckeln med en asymmetrisk publik nyckel. 
Mottagaren får sedan den krypterade datan och krypterade nyckeln. 
Mottagaren kan dekryptera datan genom att dekryptera nyckeln med sin privata nyckel.


(Förklara hur de kan användas i Java)


### Metod:
1. Metoden: Konstruera ett testprogram (vad som helst som använder minnet och processorn) och gör en mätning på det med de verktyg som du har hittat. 
Beskriv de olika stegen i mätningen, detta blir metoden.

(Jag kommer att köra ett kompilera java program som jag har skrivit själv.)

Programmet kan kryptera en fil från en förutbestämd bestämd mapp. 
Efter programmet har hittat filen och användaren har valt en krypteringsalgoritm genom ett grafiskt gränssnitt kan programmet börja. 
När programmet börjar krypterar filen skriver programmet ner vilken fil det var och när den började 
men också med jämna mellanrum tiden och hur mycket resurser programmet använder. 
När krypteringen är färdig skriver programmet ner sluttiden och 
sedan räknar det ut totala tiden (och medelvärdet av resursanvändningen).

Första- delas in i två program, ett program för att ställa in inställningar och det andra för att kryptera

I det första programmet kan man först välja vilken krypteringsalgoritm man ska ha ur en drop-down meny med en knapp som bekräftar valet. 
I det andra fältet kan man antingen skriva in sin nyckel i ett tomt fält eller välja 
från en drop-down meny vilken nyckelgenerator man ska använda och då genereras en nyckel automatiskt. 
I fältet finns det också bekräftelseknappar för vilket val man gör. 
I tredje fältet finns ett tomt fält för att skriva in meddelandet med en bekräftelseknapp eller en knapp för att få fram en filväljare. 
I sista fältet finns det en knapp för att spara ned inställningarna som har gjorts till en fil.


När det andra programmet startas med visualVM så kommer krypteringsprogrammet att göra en fil där den skriver ned när programmet startade. 
Sedan läser krypteringsprogrammet in inställningsfilen som fås från första programmet. 
Programmet hämtar meddelandet som antingen fås genom inställningsfilen eller så hämtas innehållet från filen som man vill kryptera. 
Efter att programmet hämtat meddelandet och gjort nyckeln redo att användas så kommer det att skriva ned tiden igen i samma fil 
när det är redo att börja kryptera. Programmet börjar sedan krypteringen.

Tiden och mätdatan som är före krypteringens start kan tas bort från resultaten med tidsstämplingarna från krypteringsprogrammets tidsfil. 
Då har vi den totala tiden det tog för programmet att kryptera och hur mycket resurser krypteringsprogrammet använde.

För att göra om försöket kan datan sparas från tidsfilen och VisualVM. 
När datan är sparad kan man göra om försöket genom att bara starta javaprogrammet genom VisualVM igen. 

(att läsa in den filen för att sedan få inställningarna korrekt och börja kryptera. Krypteringen går snabbt att starta med rätt inställningar.)

---

Hitta grejen i java som kan mäta resurserna som krävs för kryptering. - Magnus kollar upp det till nästa gång. 

https://visualvm.github.io/gettingstarted.html

https://www.ej-technologies.com/ 

https://www.baeldung.com/java-profilers 

### Resultat 
### Analys
### Källförteckning

Hybrid Encryption Using Java  
https://dzone.com/articles/security-implementation-of-hybrid-encryption-using 

---

Att ta göra:
- ta reda på vilka algoritmer som  ska användas och hur ska de användas
- (hur många behövs?) Välj ut de som ska användas.

- koda in algoritmerna i programmet.
- skriva färdigt programmet i intellij,  kompelera till java programmet. 

sökord:  
kryptografi,  
symmetric-key, (Symmetrisk kryptering), PKI ”Public key infrastructure”, TPM ”Trusted Platform Module”, 

--- 

#### Hybrid Encryption
Hybrid Encryption Using Java - {2022-02-01}  
https://dzone.com/articles/security-implementation-of-hybrid-encryption-using

https://www.novixys.com/blog/using-aes-rsa-file-encryption-decryption-java/

#### Symmetrisk Encryption
    aes-encryption-algorithm:	info
https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/
https://www.baeldung.com/java-cipher-input-output-stream

    aes-encryption-algorithm:	info & modes  
https://www.baeldung.com/java-aes-encryption-decryption

    aes-encryption-algorithm:
https://howtodoinjava.com/java/java-security/java-aes-encryption-example/	-s
https://www.codejava.net/coding/file-encryption-and-decryption-simple-example

	des-encryption-algorithm:		(not secure)
http://www.avajava.com/tutorials/lessons/how-do-i-encrypt-and-decrypt-files-using-des.html
https://www.youtube.com/watch?v=zn_kg55GRWo

#### Asymmetrisk Encryption
	rsa-encryption-algorithm:
https://www.novixys.com/blog/rsa-file-encryption-decryption-java/
https://www.tutorialspoint.com/java_cryptography/java_cryptography_encrypting_data.htm
https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/?ref=lbp
https://www.baeldung.com/java-rsa

https://programmer.group/rsa-encryption-and-decryption-java.html
https://www.javainterviewpoint.com/rsa-encryption-and-decryption/

---
https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html

https://dzone.com/articles/java-object-size-estimation-measuring-verifying

https://learntutorials.net/sv/java/topic/1889/rsa-kryptering

https://stackoverflow.com/questions/35993272/after-encryption-using-rsa-algorithm-what-will-be-the-size-of-1-mb-file 

---
andra som kan användas:  
Lista av libraries:  
https://kandi.openweaver.com/collections/java-encryption 

---
som troligen inte kommer att användas:  
(för pdf) external library PDFBox.  
https://www.geeksforgeeks.org/encrypt-pdf-using-java/
(image): https://www.geeksforgeeks.org/encrypt-and-decrypt-image-using-java/

övrigt som troligen inte kommer att användas  
misc:  
https://www.veracode.com/blog/research/encryption-and-decryption-java-cryptography
https://www.javamex.com/tutorials/cryptography/ciphers.shtml
https://terasolunaorg.github.io/guideline/5.3.1.RELEASE/en/Security/Encryption.html
källor:  
https://www.diva-portal.org/smash/get/diva2:538011/fulltext01.pdf (hämtad: 2021-09-22)
Extra:  
https://fileadmin.cs.lth.se/cs/Education/EDAN80/Reports/2004/BrissmyrFlink.pdf
https://www.diva-portal.org/smash/get/diva2:829573/FULLTEXT01.pdf 

--- 
### Fråga- 