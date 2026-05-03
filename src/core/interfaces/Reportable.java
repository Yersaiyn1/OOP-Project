package core.interfaces;

import models.academic.Report;

import java.io.Serializable;

/**
 * Reportable — implemented by entities that can produce a Report
 * about themselves (Student, Teacher, Transcript, ...).
 */
public interface Reportable extends Serializable {
    /**
     * Build a Report describing this entity.
     */
    Report generateReport();
}