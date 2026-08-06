/*
 * The MIT License
 *
 * Copyright (c) 2015, Frederic Gurr
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
import jenkins.model.Jenkins;

import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Job;
import hudson.model.Node;
import hudson.model.Run;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;
import io.jenkins.plugins.agent_build_history.HistoryAction;

import java.util.Set;

public class LastBuildNodeColumn extends ListViewColumn {

    @DataBoundConstructor
    public LastBuildNodeColumn() {
        super();
    }

    /**
     * @deprecated Please use getLastBuildAgents
    */
    @Deprecated
    public String getLastBuildNode(Job<?, ?> job) {
        Run<?, ?> lastBuild = job.getLastBuild();
        if (lastBuild instanceof AbstractBuild<?, ?>) {
            Node builtOn = ((AbstractBuild<?, ?>) lastBuild).getBuiltOn();
            if (builtOn instanceof Jenkins) {
                return "master";
            }
            if (builtOn != null) {
                return builtOn.getDisplayName();
            }
        } 
        return null;
    }

    public Set<String> getLastBuildAgents(Job<?, ?> job) {
        Run<?, ?> lastBuild = job.getLastBuild();
        if (lastBuild instanceof AbstractBuild<?, ?> ab) {
            Node builtOn = ab.getBuiltOn();
            if (builtOn instanceof Jenkins) {
                return Set.of("built-in");
            }
            if (builtOn != null) {
                return Set.of(builtOn.getDisplayName());
            }
        }
        
        if (Jenkins.get().getPlugin("pipeline-agent-build-history") != null) {
            if (lastBuild instanceof WorkflowRun) {
                var historyAction = ((WorkflowRun) lastBuild).getAction(HistoryAction.class);
                if (historyAction != null) {
                    return historyAction.getAgents();
                }
            }
        }
        return Set.of();
    }

    public String getLastBuildNodeDescription(Job<?, ?> job) {
        Run<?, ?> lastBuild = job.getLastBuild();
        if (lastBuild instanceof AbstractBuild<?, ?> ab) {
            Node builtOn = ab.getBuiltOn();
            if (builtOn != null) {
                return builtOn.getNodeDescription();
            }
        }
        return null;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        public DescriptorImpl() {
        }

        @Override
        @NonNull
        public String getDisplayName() {
            return Messages.LastBuildNodeColumn_DisplayName();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }
    }
}
