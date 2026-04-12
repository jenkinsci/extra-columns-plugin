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

import jenkins.model.Jenkins;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Job;
import hudson.model.Node;
import hudson.model.Run;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public class LastBuildNodeColumn extends ListViewColumn {

    @DataBoundConstructor
    public LastBuildNodeColumn() {
        super();
    }

    public String getLastBuildNode(Job<?, ?> job) {
        Run<?, ?> lastBuild = job.getLastBuild();
        if (lastBuild instanceof AbstractBuild<?, ?> ab) {
            Node builtOn = ab.getBuiltOn();
            if (builtOn instanceof Jenkins) {
                return "master";
            }
            if (builtOn != null) {
                return builtOn.getDisplayName();
            }
        } 
        return null;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        public DescriptorImpl() {
        }

        @Override
        public String getDisplayName() {
            return Messages.LastBuildNodeColumn_DisplayName();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }
    }
}
