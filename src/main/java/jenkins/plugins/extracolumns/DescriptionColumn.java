/*
 * The MIT License
 *
 * Copyright (c) 2011, Axel Haustant
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jenkins.plugins.extracolumns;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

public class DescriptionColumn extends ListViewColumn {

    private boolean displayName;
    private boolean trim;
    private int displayLength; //numbers of lines to display
    private int columnWidth;
    private boolean forceWidth;

    private final static String SEPARATOR = "<br/>";
    private final static String SEPARATORS_REGEX = "(?i)<br\\s*/>|<br>";

    @DataBoundConstructor
    public DescriptionColumn(boolean displayName, boolean trim, int displayLength, int columnWidth, boolean forceWidth) {
        super();
        this.displayName = displayName;
        this.trim = trim;
        this.displayLength = displayLength;
        this.columnWidth = columnWidth;
        this.forceWidth = forceWidth;
    }

    public DescriptionColumn() {
        this(false, false, 1, 80, false);
    }

    public boolean isDisplayName() {
        return displayName;
    }

    public boolean isTrim() {
        return trim;
    }

    public int getDisplayLength() {
        return displayLength;
    }

    public int getColumnWidth() {
        return columnWidth;
    }
    
    public boolean isForceWidth() {
        return forceWidth;
    }

    public String getToolTip(AbstractItem job) {
        return formatDescription(job, false);
    }

    public String getDescription(AbstractItem job){
        return formatDescription(job, isTrim());
    }

    private String formatDescription(AbstractItem job, boolean trimIt) {
        if (job == null) {
            return null;
        }
        if (job.getDescription() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        if (!trimIt) {
            sb.append(job.getDescription());
        } else {
            String[] parts = job.getDescription().split(SEPARATORS_REGEX);
            for (int i = 0; i < displayLength && i < parts.length; i++) {
                if (i != 0) {
                    sb.append(SEPARATOR);
                }
                sb.append(parts[i]);
            }
        }

        return sb.toString();
    }

    @Extension
    @Symbol("projectDescriptionColumn")
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.DescriptionColumn_DisplayName();
        }

        @Override
        public String getHelpFile() {
            return "/plugin/extra-columns/help-description-column.html";
        }
    }
}
