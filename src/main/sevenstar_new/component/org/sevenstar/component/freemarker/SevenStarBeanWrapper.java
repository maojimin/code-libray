package org.sevenstar.component.freemarker;

import java.util.Map;
import java.util.Set;

import freemarker.core.CollectionAndSequence;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class SevenStarBeanWrapper extends BeansWrapper {
	private static final boolean altMapWrapper = true;

	public TemplateModel wrap(Object object) throws TemplateModelException {
		if (object instanceof TemplateBooleanModel) {
			return super.wrap(object);
		}

		// attempt to get the best of both the SimpleMapModel and the MapModel
		// of FM.
		if (altMapWrapper && object instanceof Map) {
			return getInstance(object, FriendlyMapModel.FACTORY);
		}

		return super.wrap(object);
	}

	/**
	 * Attempting to get the best of both worlds of FM's MapModel and
	 * SimpleMapModel, by reimplementing the isEmpty(), keySet() and values()
	 * methods. ?keys and ?values built-ins are thus available, just as well as
	 * plain Map methods.
	 */
	private final static class FriendlyMapModel extends MapModel implements
			TemplateHashModelEx {
		static final ModelFactory FACTORY = new ModelFactory() {
			public TemplateModel create(Object object, ObjectWrapper wrapper) {
				return new FriendlyMapModel((Map) object,
						(BeansWrapper) wrapper);
			}
		};

		public FriendlyMapModel(Map map, BeansWrapper wrapper) {
			super(map, wrapper);
		}

		public boolean isEmpty() {
			return ((Map) object).isEmpty();
		}

		protected Set keySet() {
			return ((Map) object).keySet();
		}

		public TemplateCollectionModel values() {
			return new CollectionAndSequence(new SimpleSequence(((Map) object)
					.values(), wrapper));
		}
	}
}
