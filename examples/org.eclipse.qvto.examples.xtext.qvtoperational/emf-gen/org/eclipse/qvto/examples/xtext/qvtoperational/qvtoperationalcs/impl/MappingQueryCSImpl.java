/**
 */
package org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS;
import org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.MappingQueryCS;
import org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QvtoperationalcsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Mapping Query CS</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.impl.MappingQueryCSImpl#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.impl.MappingQueryCSImpl#isIsSimpleDefinition <em>Is Simple Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingQueryCSImpl
		extends MappingMethodCSImpl
		implements MappingQueryCS {

	/**
	 * The cached value of the '{@link #getExpressions() <em>Expressions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExpressions()
	 * @generated
	 * @ordered
	 */
	protected EList<ExpCS> expressions;

	/**
	 * The default value of the '{@link #isIsSimpleDefinition() <em>Is Simple Definition</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isIsSimpleDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SIMPLE_DEFINITION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsSimpleDefinition() <em>Is Simple Definition</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isIsSimpleDefinition()
	 * @generated
	 * @ordered
	 */
	protected boolean isSimpleDefinition = IS_SIMPLE_DEFINITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected MappingQueryCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QvtoperationalcsPackage.Literals.MAPPING_QUERY_CS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExpCS> getExpressions() {
		if (expressions == null) {
			expressions = new EObjectContainmentEList<ExpCS>(ExpCS.class, this, QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS);
		}
		return expressions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSimpleDefinition() {
		return isSimpleDefinition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSimpleDefinition(boolean newIsSimpleDefinition) {
		boolean oldIsSimpleDefinition = isSimpleDefinition;
		isSimpleDefinition = newIsSimpleDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QvtoperationalcsPackage.MAPPING_QUERY_CS__IS_SIMPLE_DEFINITION, oldIsSimpleDefinition, isSimpleDefinition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS:
				return ((InternalEList<?>)getExpressions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS:
				return getExpressions();
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__IS_SIMPLE_DEFINITION:
				return isIsSimpleDefinition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS:
				getExpressions().clear();
				getExpressions().addAll((Collection<? extends ExpCS>)newValue);
				return;
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__IS_SIMPLE_DEFINITION:
				setIsSimpleDefinition((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS:
				getExpressions().clear();
				return;
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__IS_SIMPLE_DEFINITION:
				setIsSimpleDefinition(IS_SIMPLE_DEFINITION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__EXPRESSIONS:
				return expressions != null && !expressions.isEmpty();
			case QvtoperationalcsPackage.MAPPING_QUERY_CS__IS_SIMPLE_DEFINITION:
				return isSimpleDefinition != IS_SIMPLE_DEFINITION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isSimpleDefinition: ");
		result.append(isSimpleDefinition);
		result.append(')');
		return result.toString();
	}

} // MappingQueryCSImpl
