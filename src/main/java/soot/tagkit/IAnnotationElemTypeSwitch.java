package soot.tagkit;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Raja Vallée-Rai and others
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

import soot.util.Switch;

public interface IAnnotationElemTypeSwitch extends Switch {
  abstract void caseAnnotationAnnotationElem(AnnotationAnnotationElem v);

  abstract void caseAnnotationArrayElem(AnnotationArrayElem v);

  abstract void caseAnnotationBooleanElem(AnnotationBooleanElem v);

  abstract void caseAnnotationClassElem(AnnotationClassElem v);

  abstract void caseAnnotationDoubleElem(AnnotationDoubleElem v);

  abstract void caseAnnotationEnumElem(AnnotationEnumElem v);

  abstract void caseAnnotationFloatElem(AnnotationFloatElem v);

  abstract void caseAnnotationIntElem(AnnotationIntElem v);

  abstract void caseAnnotationLongElem(AnnotationLongElem v);

  abstract void caseAnnotationStringElem(AnnotationStringElem v);

  abstract void defaultCase(Object object);

}
