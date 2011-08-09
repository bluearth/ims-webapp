package com.xsis.ics.util.paging;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.xsis.ics.util.paging.service.IPagingService;

public class PagingFromListWrapper<E> extends ListModelList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4693031919520133978L;
	
	 static final Logger logger = Logger.getLogger(PagingFromListWrapper.class);
	 private Paging paging;
	 private Listbox listBox;
	 private Class<E> classEntity;
	 private ListitemRenderer renderer;
	 private Object objek;
	 private final boolean supportPaging = true;
	 
	 private IPagingService pagingService;
	 
	 public PagingFromListWrapper(){
		 super();
		 
	 }
	 
	 public void init(Class<E> clazz, Paging paging, Listbox listBox, ListitemRenderer renderer){
		 setPaging(paging);
		 setListBox(listBox);
		 setClassEntity(clazz);
		 setRenderer(renderer);
		 
		 List list = pagingService.findListPaging(clazz, 0, getPaging().getPageSize());
		 
		 getPaging().setTotalSize(pagingService.getTotalRow());
		 logger.debug("Total Row --> " + getPaging().getTotalSize());
		 
		 getPaging().addEventListener("onPaging", new InPagingEventListener());
		 
//		 ListModelList lml = new ListModelList();
		 addAll(list);
		 listBox.setModel(this);
		 listBox.setItemRenderer(renderer);
	 }
	 
	 public boolean isSupportPaging() {
		return supportPaging;
	}

	public IPagingService getPagingService() {
		return pagingService;
	}

	public void setPagingService(IPagingService pagingService) {
		this.pagingService = pagingService;
	}

	public Object getObjek() {
		return objek;
	}

	public void setObjek(Object objek) {
		this.objek = objek;
	}

	public ListitemRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(ListitemRenderer renderer) {
		this.renderer = renderer;
	}

	public Class<E> getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(Class<E> classEntity) {
		this.classEntity = classEntity;
	}

	public Listbox getListBox() {
		return listBox;
	}

	public void setListBox(Listbox listBox) {
		this.listBox = listBox;
	}

	public Paging getPaging() {
		return paging;
	}
	 
	public int getPageSize() {
	        return getPaging().getPageSize();
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}
 
	public final class InPagingEventListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            PagingEvent pe = (PagingEvent) event;
            int pageNo = pe.getActivePage();
            int start = pageNo * getPaging().getPageSize();

            if (logger.isDebugEnabled()) {
                logger.debug("--> : " + start + "/" + getPaging().getPageSize());
                logger.debug("Page no --> : " + pageNo);
            }

            // refresh the list
            refreshListBox(start);
        }
    }
	
	void refreshListBox(int start){
		ListModelList lml = new ListModelList();
		int to = start + getPaging().getPageSize();
		if(!(to <= getPaging().getPageSize())){
			to = getPaging().getPageSize();
		}
//		List<Outlet> outlets = outletService.findOutletsPaging(start, to);
		List list = pagingService.findListPaging(getClassEntity(), start, to);
		lml.addAll(list);
		getListBox().setModel(lml);
		getListBox().setItemRenderer(getRenderer());
	}
}
