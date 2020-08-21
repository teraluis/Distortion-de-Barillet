# Correction de Barrillet

La distortion de Barillet est un phénomène d'optique qui se produit lorsque les conditions gausse ne sont pas respectes.

## Installation

Installer le JRE ou le JDK sur le site d'Oracle

```bash
java -version
$ /usr/libexec/java_home -v 14 --exec javac -version
```

## Usage
vous pouvez ajouster les parametres a b c et selon votre systeme optique
utilisées par le polynome du troisieme ordre (a*x^3+b*x^2+c*x+d)*x = y
```java
double paramA = 0.017715;
double paramB = 0.026731;
double paramC = 0.006731;
double paramD = 1.0 - paramA - paramB - paramC;
```
## Liens
[Approximation de gauss](https://fr.wikipedia.org/wiki/Approximation_de_Gauss)
[Distortion de Barrillet](https://mipav.cit.nih.gov/pubwiki/index.php/Barrel_Distortion_Correction)
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)