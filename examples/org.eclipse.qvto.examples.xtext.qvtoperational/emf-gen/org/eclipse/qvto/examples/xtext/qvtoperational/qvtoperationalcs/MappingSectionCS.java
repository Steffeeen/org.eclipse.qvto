/**
 */
package org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.examples.xtext.base.baseCST.ElementCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Mapping Section CS</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.MappingSectionCS#getStatements <em>Statements</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.MappingSectionCS#getMappingRuleCS <em>Mapping Rule CS</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QvtoperationalcsPackage#getMappingSectionCS()
 * @model abstract="true"
 * @generated
 */
public interface MappingSectionCS
		extends ElementCS, ElementWithBody {

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QvtoperationalcsPackage#getMappingSectionCS_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExpCS> getStatements();

	/**
	 * Returns the value of the '<em><b>Mapping Rule CS</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Rule CS</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Rule CS</em>' containment reference.
	 * @see #setMappingRuleCS(MappingRuleCS)
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QvtoperationalcsPackage#getMappingSectionCS_MappingRuleCS()
	 * @model containment="true"
	 * @generated
	 */
	MappingRuleCS getMappingRuleCS();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.MappingSectionCS#getMappingRuleCS <em>Mapping Rule CS</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Rule CS</em>' containment reference.
	 * @see #getMappingRuleCS()
	 * @generated
	 */
	void setMappingRuleCS(MappingRuleCS value);

} // MappingSectionCS
