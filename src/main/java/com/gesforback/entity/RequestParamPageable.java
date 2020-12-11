
package com.gesforback.entity;

/**
 *
 * @author sud
 */
public class RequestParamPageable {
    
    private int currentPage = 0;
    private int totalItems = 10;
    private int totalPages;

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the totalItems
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * @param totalItems the totalItems to set
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
