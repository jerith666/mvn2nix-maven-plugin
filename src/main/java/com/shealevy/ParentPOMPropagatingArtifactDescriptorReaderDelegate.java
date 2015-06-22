/*
 * Copyright (c) 2015 Shea Levy
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.shealevy;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.repository.internal.ArtifactDescriptorReaderDelegate;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;

/**
 * An <tt>ArtifactDescriptorReaderDelegate</tt> that adds any
 * &lt;parent&gt; POMs as dependencies.
 *
 * @author Shea Levy
 */
public class ParentPOMPropagatingArtifactDescriptorReaderDelegate
	extends ArtifactDescriptorReaderDelegate
{
	@Override
	public void populateResult(RepositorySystemSession session,
		ArtifactDescriptorResult result,
		Model model) {
		super.populateResult(session, result, model);
		Parent parent = model.getParent();
		if (parent != null) {
			DefaultArtifact art =
				new DefaultArtifact(parent.getGroupId(),
					parent.getArtifactId(),
					"pom",
					parent.getVersion());
			Dependency dep = new Dependency(art, "compile");
			result.addDependency(dep);
		}
	}
}
