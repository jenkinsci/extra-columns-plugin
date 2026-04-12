/*
 * The MIT License
 *
 * Copyright (c) 2019, Frederic Gurr
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
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Util;
import hudson.model.Job;
import hudson.model.Run;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public class LastBuildColumn extends ListViewColumn {

    private final int sortType;
    private final int buildType;

    private final boolean useRelative;
    private final boolean showLink;

    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    @DataBoundConstructor
    public LastBuildColumn(int sortType, int buildType, boolean useRelative, boolean showLink) {
        super();
        this.sortType = sortType;
        this.buildType = buildType;
        this.useRelative = useRelative;
        this.showLink = showLink;
    }

    public LastBuildColumn() {
        this(0, 0, false, false);
    }

    public int getSortType(){
        return sortType;
    }

    public int getBuildType(){
        return buildType;
    }

    public boolean getUseRelative() {
        return useRelative;
    }

    public boolean getShowLink() {
        return showLink;
    }

    public Run<?, ?> getBuild(Job <?, ?> job) {
        return switch (getBuildType()) {
            case 1 -> job.getLastCompletedBuild();
            case 2 -> job.getLastFailedBuild();
            case 3 -> job.getLastSuccessfulBuild();
            case 4 -> job.getLastUnsuccessfulBuild();
            case 5 -> job.getLastStableBuild();
            case 6 -> job.getLastUnstableBuild();
            default -> job.getLastBuild();
        };
    }

    public String getBuildTypeString() {
        return switch (getBuildType()) {
            case 1 -> Messages.LastBuildColumn_LastCompletedBuild();
            case 2 -> Messages.LastBuildColumn_LastFailedBuild();
            case 3 -> Messages.LastBuildColumn_LastSuccessfulBuild();
            case 4 -> Messages.LastBuildColumn_LastUnsuccessfulBuild();
            case 5 -> Messages.LastBuildColumn_LastStableBuild();
            case 6 -> Messages.LastBuildColumn_LastUnstableBuild();
            default -> Messages.LastBuildColumn_LastBuild();
        };
    }

    public String getBuildTypeUrl() {
        return switch (getBuildType()) {
            case 1 -> "lastCompletedBuild";
            case 2 -> "lastFailedBuild";
            case 3 -> "lastSuccessfulBuild";
            case 4 -> "lastUnsuccessfulBuild";
            case 5 -> "lastStableBuild";
            case 6 -> "lastUnstableBuild";
            default -> "lastBuild";
        };
    }

    public String getLastBuildStartAbsoluteString(Run<?, ?> build){
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        return sdf.format(build.getStartTimeInMillis());
    }

    public long getBuildEndTimeInMillis(Run<?, ?> build) {
        return build.getStartTimeInMillis() + build.getDuration();
    }

    public String getLastBuildEndAbsoluteString(Run<?, ?> build){
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        return sdf.format(getBuildEndTimeInMillis(build));
    }

    public String getLastBuildEndRelativeString(Run<?, ?> build){
        long duration = new GregorianCalendar().getTimeInMillis()-getBuildEndTimeInMillis(build);
        return Util.getTimeSpanString(duration);
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
            return Messages.LastBuildColumn_DisplayName();
        }

    }

}
