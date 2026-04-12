/*
 * The MIT License
 *
 * Copyright (c) 2013, Frederic Gurr
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

import edu.umd.cs.findbugs.annotations.NonNull;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.ViewJob;
import hudson.model.AbstractItem;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public class JobTypeColumn extends ListViewColumn {

    private final boolean usePronoun;

    @DataBoundConstructor
    public JobTypeColumn(boolean usePronoun) {
        super();
        this.usePronoun = usePronoun;
    }

    public JobTypeColumn() {
        this(false);
    }

    public boolean isUsePronoun(){
        return usePronoun;
    }

    /**
     * Returns the job type <br>
     * 
     * <p><b>Note:</b> The config section for this column contains an option
     * that allows to use the 'pronoun' instead of the 'simple name' to query the job type.</p>
     * 
     * @param item job, etc.
     * @return job type
     */
    public String getJobType(AbstractItem item) {
        if (isUsePronoun()) {
            return item.getPronoun();
        } else {
            return getSimpleName(item);
        }
    }

    private String getSimpleName(AbstractItem item){
        String simpleName = item.getClass().getSimpleName();
        if("Folder".equals(simpleName)){
            return Messages.JobTypeColumn_FolderName();
        } else if ("MavenModuleSet".equals(simpleName)) {
            return Messages.JobTypeColumn_MavenName();
        } else if ("MultiJobProject".equals(simpleName)) {
            return Messages.JobTypeColumn_MultiJobName();
        } else if ("IvyModuleSet".equals(simpleName)) {
            return Messages.JobTypeColumn_IvyName();
        } else if ("MatrixProject".equals(simpleName)) {
            return Messages.JobTypeColumn_MultiConfigName();
        } else if ("FreeStyleProject".equals(simpleName)) {
            return Messages.JobTypeColumn_FreestyleName();
        } else if ("WorkflowJob".equals(simpleName)) {
            return Messages.JobTypeColumn_WorkflowJobName();
        } else if ("WorkflowMultiBranchProject".equals(simpleName)) {
            return Messages.JobTypeColumn_WorkflowMultiBranchProjectName();
        } else if (item instanceof ViewJob) {
            return Messages.JobTypeColumn_ExternalName();
        }
        return "";
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        public DescriptorImpl() {
        }

        @Override
        @NonNull
        public String getDisplayName() {
            return Messages.JobTypeColumn_DisplayName();
        }

        public boolean shownByDefault() {
            return false;
        }

    }

}
