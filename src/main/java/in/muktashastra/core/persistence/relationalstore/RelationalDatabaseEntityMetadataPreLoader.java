package in.muktashastra.core.persistence.relationalstore;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.PersistableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class RelationalDatabaseEntityMetadataPreLoader implements SmartInitializingSingleton {

    private final List<String> persistableEntityPackageNames;

    @Override
    public void afterSingletonsInstantiated() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(PersistableEntity.class));
        log.info("Base packages to scan for RelationalDatabaseEntity annotations : {}", persistableEntityPackageNames);
        for(String basePackage : persistableEntityPackageNames) {
            Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);
            candidates.forEach(beanDefinition -> {
                try {
                    Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                    if(!clazz.isInterface()) {
                        RelationalDatabaseEntityMetadataCache.loadEntityMetaData(clazz);
                    }
                } catch (ClassNotFoundException | CoreException e) {
                    log.error("Error while loading RelationalDatabaseEntity with class name {} : ", beanDefinition.getBeanClassName(), e);
                }
            });
        }
    }
}
