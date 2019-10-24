package soot.xml;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2004 Jennifer Lhotak
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.tagkit.ColorTag;
import soot.tagkit.Host;
import soot.tagkit.JimpleLineNumberTag;
import soot.tagkit.LineNumberTag;
import soot.tagkit.LinkTag;
import soot.tagkit.PositionTag;
import soot.tagkit.SourceLnPosTag;
import soot.tagkit.SourcePositionTag;
import soot.tagkit.StringTag;
import soot.tagkit.Tag;

public class JavaAttribute {
  private static final Logger logger = LoggerFactory.getLogger(JavaAttribute.class);

  private int startLn;
  private ArrayList<Tag> tags;
  private ArrayList<PosColorAttribute> vbAttrs;
  public PrintWriter writerOut;

  public JavaAttribute() {
  }

  public int startLn() {
    return startLn;
  }

  public void startLn(int x) {
    startLn = x;
  }

  public ArrayList<Tag> tags() {
    return tags;
  }

  public ArrayList<PosColorAttribute> vbAttrs() {
    return vbAttrs;
  }

  public void addTag(Tag t) {
    if (tags == null) {
      tags = new ArrayList<>();
    }
    tags.add(t);
  }

  public void addVbAttr(PosColorAttribute vbAttr) {
    if (vbAttrs == null) {
      vbAttrs = new ArrayList<>();
    }
    vbAttrs.add(vbAttr);
  }

  public boolean hasColorTag() {
    if (tags != null) {
      for (Tag t : tags) {
        if (t instanceof ColorTag) {
          return true;
        }
      }
    }
    if (vbAttrs != null) {
      for (PosColorAttribute t : vbAttrs) {
        if (t.hasColor()) {
          return true;
        }
      }
    }
    return false;
  }

  private void printAttributeTag(Tag t) {
    if (t instanceof LineNumberTag) {
      int lnNum = (Integer.valueOf(((LineNumberTag) t).toString())).intValue();
      printJavaLnAttr(lnNum, lnNum);
    } else if (t instanceof JimpleLineNumberTag) {
      JimpleLineNumberTag jlnTag = (JimpleLineNumberTag) t;
      printJimpleLnAttr(jlnTag.getStartLineNumber(), jlnTag.getEndLineNumber());
    }
    /*
     * else if (t instanceof SourceLineNumberTag) { SourceLineNumberTag jlnTag = (SourceLineNumberTag)t;
     * printJavaLnAttr(jlnTag.getStartLineNumber(), jlnTag.getEndLineNumber()); }
     */
    else if (t instanceof LinkTag) {
      LinkTag lt = (LinkTag) t;
      Host h = lt.getLink();
      printLinkAttr(formatForXML(lt.toString()), getJimpleLnOfHost(h), getJavaLnOfHost(h), lt.getClassName());

    } else if (t instanceof StringTag) {
      printTextAttr(formatForXML(((StringTag) t).toString()));
    } else if (t instanceof SourcePositionTag) {
      SourcePositionTag pt = (SourcePositionTag) t;
      printSourcePositionAttr(pt.getStartOffset(), pt.getEndOffset());
    } else if (t instanceof PositionTag) {
      PositionTag pt = (PositionTag) t;
      printJimplePositionAttr(pt.getStartOffset(), pt.getEndOffset());
    } else if (t instanceof ColorTag) {
      ColorTag ct = (ColorTag) t;
      printColorAttr(ct.getRed(), ct.getGreen(), ct.getBlue(), ct.isForeground());
    } else {
      printTextAttr(t.toString());
    }
  }

  private void printJavaLnAttr(int start_ln, int end_ln) {
    writerOut.println(new StringBuilder().append("<javaStartLn>").append(start_ln).append("</javaStartLn>").toString());
    writerOut.println(new StringBuilder().append("<javaEndLn>").append(end_ln).append("</javaEndLn>").toString());
  }

  private void printJimpleLnAttr(int start_ln, int end_ln) {
    writerOut.println(new StringBuilder().append("<jimpleStartLn>").append(start_ln).append("</jimpleStartLn>").toString());
    writerOut.println(new StringBuilder().append("<jimpleEndLn>").append(end_ln).append("</jimpleEndLn>").toString());
  }

  private void printTextAttr(String text) {
    writerOut.println(new StringBuilder().append("<text>").append(text).append("</text>").toString());
  }

  private void printLinkAttr(String label, int jimpleLink, int javaLink, String className) {
    writerOut.println("<link_attribute>");
    writerOut.println(new StringBuilder().append("<link_label>").append(label).append("</link_label>").toString());
    writerOut.println(new StringBuilder().append("<jimple_link>").append(jimpleLink).append("</jimple_link>").toString());
    writerOut.println(new StringBuilder().append("<java_link>").append(javaLink).append("</java_link>").toString());
    writerOut.println(new StringBuilder().append("<className>").append(className).append("</className>").toString());
    writerOut.println("</link_attribute>");
  }

  private void startPrintValBoxAttr() {
    writerOut.println("<value_box_attribute>");
  }

  private void printSourcePositionAttr(int start, int end) {
    writerOut.println(new StringBuilder().append("<javaStartPos>").append(start).append("</javaStartPos>").toString());
    writerOut.println(new StringBuilder().append("<javaEndPos>").append(end).append("</javaEndPos>").toString());
  }

  private void printJimplePositionAttr(int start, int end) {
    writerOut.println(new StringBuilder().append("<jimpleStartPos>").append(start).append("</jimpleStartPos>").toString());
    writerOut.println(new StringBuilder().append("<jimpleEndPos>").append(end).append("</jimpleEndPos>").toString());
  }

  private void printColorAttr(int r, int g, int b, boolean fg) {
    writerOut.println(new StringBuilder().append("<red>").append(r).append("</red>").toString());
    writerOut.println(new StringBuilder().append("<green>").append(g).append("</green>").toString());
    writerOut.println(new StringBuilder().append("<blue>").append(b).append("</blue>").toString());
    if (fg) {
      writerOut.println("<fg>1</fg>");
    } else {
      writerOut.println("<fg>0</fg>");
    }
  }

  private void endPrintValBoxAttr() {
    writerOut.println("</value_box_attribute>");
  }

  // prints all tags
  public void printAllTags(PrintWriter writer) {
    this.writerOut = writer;
    if (tags != null) {
      tags.forEach(this::printAttributeTag);
    }
    if (vbAttrs == null) {
		return;
	}
	vbAttrs.stream().filter(PosColorAttribute::hasColor).forEach(attr -> {
	  startPrintValBoxAttr();
	  printSourcePositionAttr(attr.javaStartPos(), attr.javaEndPos());
	  printJimplePositionAttr(attr.jimpleStartPos(), attr.jimpleEndPos());
	  // printColorAttr(attr.color().red(), attr.color().green(), attr.color().blue(), attr.color().fg());
	  endPrintValBoxAttr();
	});
  }

  // prints only tags related to strings and links (no pos tags)
  public void printInfoTags(PrintWriter writer) {
    this.writerOut = writer;
    tags.forEach(this::printAttributeTag);
  }

  private String formatForXML(String in) {
    in = in.replaceAll("<", "&lt;");
    in = in.replaceAll(">", "&gt;");
    in = in.replaceAll("&", "&amp;");
    return in;
  }

  private int getJavaLnOfHost(Host h) {
    Iterator<Tag> it = h.getTags().iterator();
    while (it.hasNext()) {
      Tag t = it.next();
      // logger.debug(""+t.getClass().toString());
      if (t instanceof SourceLnPosTag) {
        // logger.debug("t is LineNumberTag");
        return ((SourceLnPosTag) t).startLn();
      } else if (t instanceof LineNumberTag) {
        return (Integer.valueOf(((LineNumberTag) t).toString())).intValue();
      }
    }
    return 0;
  }

  private int getJimpleLnOfHost(Host h) {
    Iterator<Tag> it = h.getTags().iterator();
    while (it.hasNext()) {
      Tag t = it.next();
      if (t instanceof JimpleLineNumberTag) {
        return ((JimpleLineNumberTag) t).getStartLineNumber();
      }
    }
    return 0;
  }
}
