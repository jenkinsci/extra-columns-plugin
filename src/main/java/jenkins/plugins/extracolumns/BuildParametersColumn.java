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

import hudson.Extension;
import hudson.model.Action;
import hudson.model.ParameterValue;
import hudson.model.Job;
import hudson.model.ParametersAction;
import hudson.model.Run;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

public class BuildParametersColumn extends ListViewColumn {

    private boolean useRegex;
    private String regex;

    @DataBoundConstructor
    public BuildParametersColumn(boolean useRegex, String regex) {
        super();
        this.useRegex = useRegex;
        this.regex = regex;
    }

    public BuildParametersColumn() {
        this(false, "");
    }

    public boolean isUseRegex(){
        return useRegex;
    }

    public String getRegex(){
        return regex;
    }

    public List<String> getBuildParameters(Job<?, ?> job) {
        if (job == null || job.getLastBuild() == null) {
            return Collections.singletonList("");
        }
        Run<?, ?> r = job.getLastBuild();
        List<String> strings = new ArrayList<String>();
        for(Action action : r.getAllActions()) {
            if(action instanceof ParametersAction pa) {
                for (ParameterValue p : pa.getParameters()) {
                    if(!isUseRegex() || p.getName().matches(regex)){
                        strings.add(p.getShortDescription());
                    }
                }
            }
        }
        return strings;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.BuildParametersColumn_DisplayName();
        }

    }
}
