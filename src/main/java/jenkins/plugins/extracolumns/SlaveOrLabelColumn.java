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
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.model.Label;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * View column that shows build processor or build processor label restriction
 * of a job.
 * 
 * @author krulls
 */
public class SlaveOrLabelColumn extends ListViewColumn {

    private static final Logger LOGGER = Logger
            .getLogger(SlaveOrLabelColumn.class.getName());

    @DataBoundConstructor
    public SlaveOrLabelColumn() {
    }

    public String getInfo(Job<?, ?> job) {

        if(!(job instanceof AbstractProject)){
            LOGGER.finest("Not an instance of " + AbstractProject.class.getCanonicalName() + ". Cannot get info.");
            return "";
        }
        
        AbstractProject<?, ?> project = AbstractProject.class.cast(job);
        Label projectLabel = project.getAssignedLabel();
        if (projectLabel == null || projectLabel.isEmpty())
            return "N/A";

        if (projectLabel.isSelfLabel())
            return projectLabel.getName();

        return projectLabel.getName()
                + ((projectLabel.getDescription() == null || projectLabel
                        .getDescription().length() < 1) ? "" : " ("
                        + projectLabel.getDescription() + ")");
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {
        @Override
        public String getDisplayName() {
            return Messages.SlaveOrLabelColumn_DisplayName();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }
    }
}
