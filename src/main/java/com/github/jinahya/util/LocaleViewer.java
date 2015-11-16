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

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class LocaleViewer {

    private enum Column {
        INDEX() {
            @Override
            Object value(int row, int column, final Locale locale) {
                if (row == 0) {
                    System.out.println("locale: " + locale.toString());
                }
                return row;
            }
        },
        NAME() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.toString();
            }
        },
        LANGUAGE() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayLanguage(locale);
            }
        },
        COUNTRY {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getDisplayCountry(locale);
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
        },
        LN() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getLanguage();
            }
        },
        CO() {
            @Override
            Object value(int row, int column, final Locale locale) {
                return locale.getCountry();
            }
        };

        abstract Object value(int row, int column, Locale locale);
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
                            protected TableModel createDefaultDataModel() {
                                final Locale[] rows
                                        = Locale.getAvailableLocales();
                                final Column[] columns = Column.values();
                                return new AbstractTableModel() {

                                    @Override
                                    public int getRowCount() {
                                        return rows.length;
                                    }

                                    @Override
                                    public int getColumnCount() {
                                        return columns.length;
                                    }

                                    @Override
                                    public String getColumnName(final int c) {
                                        return columns[c].name();
                                    }

                                    @Override
                                    public Object getValueAt(final int r,
                                                             final int c) {
                                        return columns[c].value(r, c, rows[r]);
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
