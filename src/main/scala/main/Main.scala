package main

import java.io.StringReader
import org.apache.fop.apps.FopFactory
import javax.xml.transform.TransformerFactory
import java.io.OutputStream
import javax.xml.transform.sax.SAXResult
import java.io.File
import javax.xml.transform.stream.StreamSource
import java.util.Date
import org.apache.xmlgraphics.util.MimeConstants
import java.io.FileOutputStream
import org.apache.commons.io.FileUtils
import java.awt.Desktop

object Main {

  def main(args: Array[String]): Unit = {

    val target = new File("test.pdf")
    val sourceSVG = new File("test.svg")

    val fileStream = new FileOutputStream(target)
    transform(fileStream, sourceSVG)

    Desktop.getDesktop.open(target)
  }

  protected def transform(out: OutputStream, svg: File) = {
    val factory = TransformerFactory.newInstance()
    val transformer = factory.newTransformer()
    val fopFactory = FopFactory.newInstance(new File(".").toURI())
    val userAgent = fopFactory.newFOUserAgent()
    userAgent.setCreationDate(new Date)
    val fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, out)
    val src = new StreamSource(new StringReader(template(svg)))
    val res = new SAXResult(fop.getDefaultHandler())

    transformer.transform(src, res);

    fop
  }

  protected def template(sourceSVG: File) = {

    val foxml = {
      <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
        <fo:layout-master-set>
          <fo:simple-page-master margin-right="1.5cm" margin-left="1.5cm" margin-bottom="2cm" margin-top="1cm" master-name="first">
            <fo:region-body margin-top="1cm"/>
            <fo:region-before extent="1cm"/>
            <fo:region-after extent="1.5cm"/>
          </fo:simple-page-master>
        </fo:layout-master-set>
        <fo:page-sequence master-reference="first">
          <fo:static-content flow-name="xsl-region-after">
            <fo:block-container margin-top="0.5cm">
              <fo:block text-align="center" font-size="12pt" color="red">
                TEST:<fo:page-number/>
                /<fo:page-number-citation ref-id="last-page"/>
              </fo:block>
            </fo:block-container>
          </fo:static-content>
          <fo:flow flow-name="xsl-region-body">
            <fo:block margin-left="7.5cm">
              <fo:instream-foreign-object xmlns="http://www.w3.org/2000/svg" content-width="scale-to-fit" content-height="200%">
                { parse(sourceSVG) }
              </fo:instream-foreign-object>
            </fo:block>
            <fo:block id="last-page"></fo:block>
          </fo:flow>
        </fo:page-sequence>
      </fo:root>
    }

    foxml.toString()
  }

  private def parse(sourceSVG: File) = {

    val svgString = FileUtils.readFileToString(sourceSVG)

    xml.XML.loadString(svgString)
  }

}