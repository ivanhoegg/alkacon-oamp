/*
 * File   : $Source: /alkacon/cvs/alkacon/com.alkacon.opencms.formgenerator/src/com/alkacon/opencms/formgenerator/CmsCheckboxField.java,v $
 * Date   : $Date: 2008/01/17 15:24:55 $
 * Version: $Revision: 1.2 $
 *
 * This file is part of the Alkacon OpenCms Add-On Module Package
 *
 * Copyright (c) 2007 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * The Alkacon OpenCms Add-On Module Package is free software: 
 * you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The Alkacon OpenCms Add-On Module Package is distributed 
 * in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the Alkacon OpenCms Add-On Module Package.  
 * If not, see http://www.gnu.org/licenses/.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com.
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org.
 */

package com.alkacon.opencms.formgenerator;

import org.opencms.i18n.CmsMessages;
import org.opencms.util.CmsStringUtil;

import java.util.Iterator;

/**
 * Represents a check box.<p>
 * 
 * @author Thomas Weckert 
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 7.0.4 
 */
public class CmsCheckboxField extends A_CmsField {

    /** HTML field type: checkbox. */
    private static final String TYPE = "checkbox";

    /**
     * @see com.alkacon.opencms.formgenerator.I_CmsField#getType()
     */
    public String getType() {

        return TYPE;
    }

    /**
     * Returns the type of the input field, e.g. "text" or "select".<p>
     * 
     * @return the type of the input field
     */
    public static String getStaticType() {

        return TYPE;
    }

    /**
     * @see com.alkacon.opencms.formgenerator.I_CmsField#buildHtml(CmsFormHandler, org.opencms.i18n.CmsMessages, String, boolean)
     */
    public String buildHtml(CmsFormHandler formHandler, CmsMessages messages, String errorKey, boolean showMandatory) {

        StringBuffer buf = new StringBuffer();
        String fieldLabel = getLabel();
        String errorMessage = "";
        String mandatory = "";

        if (CmsStringUtil.isNotEmpty(errorKey)) {

            if (CmsFormHandler.ERROR_MANDATORY.equals(errorKey)) {
                errorMessage = messages.key("form.error.mandatory");
            } else if (CmsStringUtil.isNotEmpty(getErrorMessage())) {
                errorMessage = getErrorMessage();
            } else {
                errorMessage = messages.key("form.error.validation");
            }

            errorMessage = messages.key("form.html.error.start") + errorMessage + messages.key("form.html.error.end");
            fieldLabel = messages.key("form.html.label.error.start")
                + fieldLabel
                + messages.key("form.html.label.error.end");
        }

        if (isMandatory() && showMandatory) {
            mandatory = messages.key("form.html.mandatory");
        }

        // line #1
        if (showRowStart(messages.key("form.html.col.two"))) {
            buf.append(messages.key("form.html.row.start")).append("\n");
        }

        // line #2
        buf.append(messages.key("form.html.label.start")).append(fieldLabel).append(mandatory).append(
            messages.key("form.html.label.end")).append("\n");

        // line #3
        buf.append(messages.key("form.html.field.start")).append("\n");

        boolean showInRow = false;
        // add the items
        Iterator i = getItems().iterator();
        while (i.hasNext()) {

            CmsFieldItem curOption = (CmsFieldItem)i.next();
            showInRow = curOption.isShowInRow();
            String checked = "";
            if (curOption.isSelected()) {
                checked = " checked=\"checked\"";
            }

            if (showInRow) {
                // create different HTML for row output
                buf.append(messages.key("form.html.checkbox.row.input.start"));
                buf.append("<input type=\"checkbox\" name=\"").append(getName()).append("\" value=\"").append(
                    curOption.getValue()).append("\"").append(checked).append(" class=\"check\"/>");
                buf.append(messages.key("form.html.checkbox.row.input.end"));
                buf.append(messages.key("form.html.checkbox.row.label.start"));
                buf.append(curOption.getLabel());
                buf.append(messages.key("form.html.checkbox.row.label.end"));
                if (i.hasNext()) {
                    buf.append(messages.key("form.html.checkbox.row.seperator"));
                }
            } else {
                buf.append(messages.key("form.html.checkbox.input.start"));
                buf.append("<input type=\"checkbox\" name=\"").append(getName()).append("\" value=\"").append(
                    curOption.getValue()).append("\"").append(checked).append(" class=\"check\"/>");
                buf.append(messages.key("form.html.checkbox.input.end"));
                buf.append(messages.key("form.html.checkbox.label.start"));
                buf.append(curOption.getLabel());
                buf.append(messages.key("form.html.checkbox.label.end"));
                if (i.hasNext()) {
                    buf.append(messages.key("form.html.checkbox.seperator"));
                }
            }

            if (i.hasNext()) {
                buf.append(messages.key("form.html.checkbox.seperator"));
            }

            buf.append("\n");
        }

        buf.append(errorMessage).append("\n");

        buf.append(messages.key("form.html.field.end")).append("\n");

        if (showRowEnd(messages.key("form.html.col.two"))) {
            buf.append(messages.key("form.html.row.end")).append("\n");
        }
        return buf.toString();
    }

}
