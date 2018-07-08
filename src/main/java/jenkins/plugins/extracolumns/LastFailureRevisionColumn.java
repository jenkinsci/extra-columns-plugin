/*
 * The MIT License
 *
 * Copyright (c) 2012, Frederic Gurr
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

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.AbstractBuild;
import hudson.model.Job;
import hudson.model.Run;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;
import hudson.scm.*;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.Entry;

public class LastFailureRevisionColumn extends ListViewColumn {


    @DataBoundConstructor
    public LastFailureRevisionColumn() {
        super();
    }

    public String getRevNum(@SuppressWarnings("rawtypes") Job job) {
        AbstractProject project = (AbstractProject) job;
        AbstractBuild build = (AbstractBuild) project.getLastFailedBuild();
        Object[] items = build.getChangeSet().getItems();
        Entry item= (Entry) items[0];
        String commitID=item.getCommitId();
        return commitID;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.LastFailureRevisionColumn_DisplayName();
        }

    }
}
