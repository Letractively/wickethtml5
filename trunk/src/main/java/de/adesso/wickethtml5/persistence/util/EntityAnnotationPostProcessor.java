package de.adesso.wickethtml5.persistence.util;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * Spring PostProcessor, der @Entity-Annotationen auswertet und einer
 * PersistenceUnit mitteilt, dass diese Java-Klassen als Entities behandelt
 * werden sollen.
 * 
 * @author hombergs
 * 
 */
public class EntityAnnotationPostProcessor implements PersistenceUnitPostProcessor {

	@Autowired
	private ResourcePatternResolver resourceLoader;

	private List<String> packagesToScan;

	@Override
	public void postProcessPersistenceUnitInfo(final MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
		try {
			for (final String packageToScan : packagesToScan) {
				final String packageResource = packageToScan.replaceAll("\\.", "/");
				final Resource[] resources = resourceLoader.getResources("classpath:" + packageResource + "/*.class");
				for (final Resource resource : resources) {
					final CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
					final MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
					if (metadataReader.getAnnotationMetadata().isAnnotated(javax.persistence.Entity.class.getName())) {
						mutablePersistenceUnitInfo.addManagedClassName(metadataReader.getClassMetadata().getClassName());
					}
				}
			}
			mutablePersistenceUnitInfo.setExcludeUnlistedClasses(true);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getPackagesToScan() {
		return packagesToScan;
	}

	@Required
	public void setPackagesToScan(final List<String> packageNames) {
		this.packagesToScan = packageNames;
	}
}
