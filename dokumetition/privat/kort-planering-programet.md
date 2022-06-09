## controller:
kan hämta inställningar från view  
sätta in och starta modellen med räta inställningar  
skapar en fil som den skriver ner tider i

## view:
läsa in en fil med inställningarna, filvägen/filnamnet, eventuellt nyckelns filmväg  
eller  
en grafisk interface bär man ställer in inställningarna  
(ett extra program som man kan ställa in inställningarna i objektsform)

## model:

en klass som väljer rätt krypteringsalgoritm, hämtar eventuell nyckel från en fil, läser in filen om den kan bli separerat från krypteringsalgoritmen, 
skriver ner när krypteringsalgoritmen började och när den är färdig i en fil (samma som i controllern), 
eventuellt att den sparar ner innehållet av det krypterade datan

egna klasser för varje krypteringsalgoritm och får in datan som den behöver, 
antingen ger det krypterade meddelandet tillbaka eller sparar krypterade datan själv


## Klasser:
egen data strukturer som har alla inställningar och filens väg och nyckeln eller dess väg
