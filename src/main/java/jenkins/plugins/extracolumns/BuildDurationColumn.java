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
import java.util.GregorianCalendar;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Util;
import hudson.model.Job;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public class BuildDurationColumn extends ListViewColumn {

    private final int buildDurationType;

    @DataBoundConstructor
    public BuildDurationColumn(int buildDurationType) {
        super();
        this.buildDurationType = buildDurationType;
    }

    public BuildDurationColumn() {
        this(0);
    }

    public int getBuildDurationType(){
        return buildDurationType;
    }

    public long getSortDataBuildRunningSince(Job<?, ?> job){
        return new GregorianCalendar().getTimeInMillis()-job.getLastBuild().getTimeInMillis();
    }

    public String getAverageBuildDurationString(Job<?, ?> job){
        return Util.getTimeSpanString(job.getLastBuild().getEstimatedDuration());
    }

    public String getAverageBuildDurationMinsString(Job<?, ?> job){
        return String.valueOf(job.getLastBuild().getEstimatedDuration()/1000/60);
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        @NonNull
        public String getDisplayName() {
            return Messages.BuildDurationColumn_DisplayName();
        }

    }

}
