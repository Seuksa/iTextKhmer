# iTextKhmer
The library to extend the support for Khmer language rendering for PDF using iText.

The rendering is using the a class from the Mobile project of Nokor Group (AKA: Nokor-IT).

Currently the library is supported with fonts: 
- Khmer OS Battambang

Tested fonts but not working:
- Nokora

# Dependencies

- itextpdf, version 5.5.8

# How to use

With maven:

    <dependency>
		<groupId>com.itextpdf</groupId>
		<artifactId>itextpdf</artifactId>
		<version>5.5.8</version>
	</dependency>
	<dependency>
		<groupId>org.seuksa.itextkhmer</groupId>
		<artifactId>iTextKhmer</artifactId>
		<version>1.0-SNAPSHOT</version>
	</dependency>

# Sample Projects
Please go to following links for sample project using this library:
- https://github.com/osify/iTextKhmerSample
  Explain article (http://ask.osify.com/qa/613)

# Authors

- Pongsametrey S. <metrey@osify.com> : Add support with itext
- Pengleng Huot/Nokor-Group (for rendering)

# License
- iTextKhmer: Apache 2.0
- itextpdf (see itextpdf license from official website)