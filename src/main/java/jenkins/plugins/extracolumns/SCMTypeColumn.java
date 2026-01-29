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

import java.util.Collection;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.scm.SCM;
import hudson.views.ListViewColumnDescriptor;
import jenkins.model.Jenkins;
import hudson.views.ListViewColumn;

public class SCMTypeColumn extends ListViewColumn {


    @DataBoundConstructor
    public SCMTypeColumn() {
        super();
    }

    public String getScmType(@SuppressWarnings("rawtypes") Job job) {
        if(job instanceof AbstractProject<?, ?> project) {
            return project.getScm().getDescriptor().getDisplayName();
        } else {
            String simpleName = job.getClass().getSimpleName();
            if ("WorkflowJob".equals(simpleName)) {
                Jenkins instance = Jenkins.get();
                if (instance.getPlugin("workflow-job") != null) {
                    org.jenkinsci.plugins.workflow.job.WorkflowJob wfj = (org.jenkinsci.plugins.workflow.job.WorkflowJob) job;
                    Collection<? extends SCM> scms = wfj.getSCMs();
                    if (scms.isEmpty()) {
                        return "N/A";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (SCM scm : scms) {
                        sb.append(scm.getDescriptor().getDisplayName()).append("\n");
                    }
                    return sb.toString();
                }
            }
        }
        return "N/A";
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.SCMTypeColumn_DisplayName();
        }

    }
}
