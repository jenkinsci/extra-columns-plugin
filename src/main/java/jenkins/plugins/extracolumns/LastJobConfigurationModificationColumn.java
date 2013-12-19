/*
 * The MIT License
 *
 * Copyright (c) 2013, Stephan Krull
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

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Job;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.FastDateFormat;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * View column that shows the date of last job configuration modification.
 * 
 * @author Stephan Krull, ECG Leipzig GmbH
 * 
 */
public class LastJobConfigurationModificationColumn extends ListViewColumn {

    private static final Logger LOGGER = Logger
            .getLogger(LastJobConfigurationModificationColumn.class.getName());

    @DataBoundConstructor
    public LastJobConfigurationModificationColumn() {
    }

    public String getInfo(Job<?, ?> job) {
        XmlFile config = job.getConfigFile();
        if (config == null || !config.exists())
            return "N/A";

        try {
            long lm = config.getFile().lastModified();
            return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(
                    new Date(lm));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,
                    "Cannot read last modification date of configuration for job '"
                            + job.getName() + "'.", e);
            return "N/A";
        }

    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {
        @Override
        public String getDisplayName() {
            return Messages
                    .LastJobConfigurationModificationColumn_DisplayName();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }

    }
}
