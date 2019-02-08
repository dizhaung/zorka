/*
 * Copyright 2012-2019 Rafal Lewczuk <rafal.lewczuk@jitlogic.com>
 * <p/>
 * This is free software. You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jitlogic.zorka.core.spy;

import com.jitlogic.zorka.common.util.ZorkaLog;
import com.jitlogic.zorka.common.util.ZorkaLogger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;


public class SpyClassWriter extends ClassWriter {

    private static final ZorkaLog log = ZorkaLogger.getLog(SpyClassWriter.class);

    private ClassLoader loader;
    private SpyClassResolver resolver;

    SpyClassWriter(ClassReader classReader, int i, ClassLoader loader, SpyClassResolver resolver) {
        super(classReader, i);
        this.loader = loader;
        this.resolver = resolver;
    }


    @Override
    protected String getCommonSuperClass(final String type1, final String type2) {
        String rslt;
        if (resolver == null) {
            rslt = super.getCommonSuperClass(type1, type2);
        } else {
            rslt = resolver.getCommonSuperClass(loader,
                        type1.replace('/', '.'),
                        type2.replace('/', '.'))
                .replace('.', '/');
        }

        rslt = rslt != null ? rslt.replace('.', '/') : "java/lang/Object";

        if (ZorkaLogger.isLogMask(ZorkaLogger.ZSP_CLASS_TRC)) {
            log.trace(ZorkaLogger.ZSP_CLASS_TRC, "getCommonSuperclass('" + type1 + "', '" + type2 + "') -> '" + rslt + "'");
        }

        return rslt;
    }

}
