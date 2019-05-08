/*
 * Copyright 2014 Jin Kwon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.util;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

public class LocaleViewer {

    private enum Column {

        NAME() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.toString();
            }
        },
        LANGUAGE() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getLanguage();
            }
        },
        LANGUAGE_DL() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayLanguage(locale);
            }
        },
        LANGUAGE_DU() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayLanguage();
            }
        },
        LANGUAGE_US() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayLanguage(Locale.US);
            }
        },
        SCRIPT {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getScript();
            }
        },
        SCRIPT_DL {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayScript(locale);
            }
        },
        SCRIPT_DU {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayScript();
            }

            @Override
            String toolTipText() {
                return "getDisplayScript()";
            }
        },
        SCRIPT_US {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayScript(Locale.US);
            }

            @Override
            String toolTipText() {
                return "getDisplayScript(Locale.US)";
            }
        },
        COUNTRY {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getCountry();
            }
        },
        COUNTRY_DL {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayCountry(locale);
            }
        },
        COUNTRY_DU {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayCountry();
            }
        },
        COUNTRY_US {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayCountry(Locale.US);
            }
        },
        VARIANT {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getVariant();
            }
        },
        VARIANT_DL {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayVariant(locale);
            }
        },
        VARIANT_DU {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayVariant();
            }
        },
        VARIANT_US {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayVariant(Locale.US);
            }

            @Override
            String toolTipText() {
                return "getDisplayVariant(Locale.US)";
            }
        },
        LAN() {
            @Override
            Object value(int row, int column, final Locale locale) {
                try {
                    return locale.getISO3Language();
                } catch (final MissingResourceException mre) {
                    return "N/A";
                }
            }

            @Override
            String toolTipText() {
                return "getISO3Language()";
            }
        },
        CON() {
            @Override
            Object value(int row, int column, final Locale locale) {
                try {
                    return locale.getISO3Country();
                } catch (final MissingResourceException mre) {
                    return "N/A";
                }
            }
        };

        static final Column[] VALUES = values();

        abstract Object value(int row, int column, Locale locale);

        String toolTipText() {
            return null;
        }
    }

    public static void main(final String[] args) {
        new JFrame("Locale Viewer") {
            @Override
            protected JRootPane createRootPane() {
                return new JRootPane() {
                    @Override
                    protected Container createContentPane() {
                        return new JScrollPane(new JTable() {
                            @Override
                            protected JTableHeader createDefaultTableHeader() {
                                final JTableHeader defaultTableHeader = super.createDefaultTableHeader();
                                return new JTableHeader(defaultTableHeader.getColumnModel()) {
                                    @Override
                                    public String getToolTipText(final MouseEvent event) {
                                        final Point point = event.getPoint();
                                        final int columnIndex = getColumnModel().getColumnIndexAtX(point.x);
                                        int modelIndex = getColumnModel().getColumn(columnIndex).getModelIndex();
                                        return Column.VALUES[modelIndex].toolTipText();
                                    }
                                };
                            }

                            @Override
                            protected TableModel createDefaultDataModel() {
                                //final Locale[] rows = Locale.getAvailableLocales();
                                final List<Locale> rows = Arrays.stream(Locale.getAvailableLocales())
                                        .filter(v -> v != Locale.ROOT)
                                        .sorted(Comparator.comparing(Locale::getDisplayName))
                                        .collect(Collectors.toList());
                                return new AbstractTableModel() {
                                    @Override
                                    public int getRowCount() {
                                        return rows.size();
                                    }

                                    @Override
                                    public int getColumnCount() {
                                        return Column.VALUES.length;
                                    }

                                    @Override
                                    public String getColumnName(final int c) {
                                        return Column.VALUES[c].name();
                                    }

                                    @Override
                                    public Object getValueAt(final int r, final int c) {
                                        return Column.VALUES[c].value(r, c, rows.get(r));
                                    }
                                };
                            }
                        });
                    }
                };
            }

            @Override
            protected void processWindowEvent(final WindowEvent we) {
                super.processWindowEvent(we);
                if (we.getID() == WindowEvent.WINDOW_CLOSING) {
                    System.exit(0);
                }
            }

            @Override
            public void frameInit() {
                super.frameInit();
                pack();
            }
        }.setVisible(true);
    }
}
